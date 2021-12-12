import React, { Component } from "react";
import { Modal } from "react-bootstrap";
import Axios from "axios";
import { BASE_URL_PAYPAL, BASE_URL_AGENT } from "../constants.js";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { YMaps, Map } from "react-yandex-maps";
import qr from '../static/qr.png';
import bank_cards from '../static/bank_cards.jpg';
import bitcoin from '../static/bitcoin.png';
import paypal from '../static/paypall.png';
import ModalDialog from "../components/ModalDialog";

import getAuthHeader from "../GetHeader";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import CheckoutForm from "../pages/CheckoutForm";
import "../App.css";

const stripePromise = loadStripe("pk_test_51K43vfCY3zNGAGpLz1eYNNh5vcUn5n3MSNQNYh789Gm9BvT8NJubPLKXLUb0tdPhVDzovZWlzF9dk6sFGq2l2WNe00rU2oDg4n");

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};
const appearance = {
	theme: 'stripe',
};

class Address extends Component {
	state = {
		quantity: "",
		errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		address: "",
		addressError: "none",
		addressNotFoundError: "none",
		openModal: false,
		coords: [],
		isPaypalAllowed: true,
		isBankCardAllowed: true,
		isQRAllowed: true,
		isBitcoinAllowed: true,
		clentSecret: "",
		options: {},
		cardSelected: false,
		
	};



	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
		console.log(this.isBankCardAllowed)
		console.log(this.isPaypalAllowed)
		console.log(this.isBitcoinAllowed)
	}
	handleModalClose = () => {
		this.setState({ openModal: false })
		window.location.reload();
	}

	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	validateForm = () => {
		this.setState({

			addressError: "none",
			addressNotFoundError: "none",

		});

		if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		}

		return true;
	};
	componentDidMount() {
	
	}

	handleQuantityChange = (event) => {

		this.setState({ quantity: event.target.value });

	};
	handleBankCardClicked = (event) => {

		this.setState({ cardSelected: true });
		


	};
	getPrice = (products) => {
		let price = 0;
		for (let i = 0; i < products.length; i++) {
			price = price + products[i].quantity * products[i].price
		}
		return price
	}

	handlePayPalClicked = () => {
		let id = localStorage.getItem("userId").substring(1, localStorage.getItem('userId').length - 1);
		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let found = true;
		this.ymaps
			.geocode(this.addressInput.current.value, {
				results: 1,
			})
			.then(function (res) {
				if (typeof res.geoObjects.get(0) === "undefined") found = false;
				else {
					var firstGeoObject = res.geoObjects.get(0),
						coords = firstGeoObject.geometry.getCoordinates();
					latitude = coords[0];
					longitude = coords[1];
					country = firstGeoObject.getCountry();
					street = firstGeoObject.getThoroughfare();
					city = firstGeoObject.getLocalities().join(", ");
				}
			})
			.then((res) => {
				var foundAddress = { street, country, city, latitude, longitude }
				var products = this.props.products


				if (this.validateForm()) {
					if (found === false) {
						this.setState({ addressNotFoundError: "initial" });
					} else {
						localStorage.setItem("orderAddress", JSON.stringify(foundAddress));
						localStorage.setItem("orderProducts", JSON.stringify(products));

						const checkoutDTO = {
							price: this.getPrice(products),
							currency: "USD",
							method: "PAYPAL",
							intent: "SALE",
							description: "description"
						}

						Axios.post(BASE_URL_PAYPAL + "/api/paypal/pay", checkoutDTO)
							.then((res) => {
								const data = res.data
								window.location.href = data
							}
							)
							.catch(err => console.log(err));
					}
				}
			});
	}
	handleBankCard = () => {
        let id = localStorage.getItem("userId").substring(1, localStorage.getItem('userId').length - 1);
		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let found = true;
		this.ymaps
			.geocode(this.addressInput.current.value, {
				results: 1,
			})
			.then(function (res) {
				if (typeof res.geoObjects.get(0) === "undefined") found = false;
				else {
					var firstGeoObject = res.geoObjects.get(0),
						coords = firstGeoObject.geometry.getCoordinates();
					latitude = coords[0];
					longitude = coords[1];
					country = firstGeoObject.getCountry();
					street = firstGeoObject.getThoroughfare();
					city = firstGeoObject.getLocalities().join(", ");
				}
			})
			.then((res) => {
				let userDTO = {
					address: { street, country, city, latitude, longitude },
					products: this.props.products,
                    
				};
				console.log(userDTO);

				if (this.validateForm(userDTO)) {
					if (found === false) {
						this.setState({ addressNotFoundError: "initial" });
					} else {
						console.log(userDTO);
						Axios.post(BASE_URL_AGENT + "/api/purchase/add", userDTO, {  headers: { Authorization: getAuthHeader() } })
							.then((res) => {
								if (res.status === 409) {
									this.setState({
										errorHeader: "Resource conflict!",
										errorMessage: "Email already exist.",
										hiddenErrorAlert: false,
									});
								} else if (res.status === 500) {
									this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
								} else {
									
									this.setState({openModal : true})

		
								}
							})
							.catch((err) => {
								console.log(err);
							});
					}
				}
			});
	};

	
	render() {

		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-250w-150h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>


					<div className="control-group">
						<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Address:</label>
							<input className="form-control" id="suggest" ref={this.addressInput} placeholder="Address" />
						</div>
						<YMaps
							query={{
								load: "package.full",
								apikey: "b0ea2fa3-aba0-4e44-a38e-4e890158ece2",
								lang: "en_RU",
							}}
						>
							<Map
								style={{ display: "none" }}
								state={mapState}
								onLoad={this.onYmapsLoad}
								instanceRef={(map) => (this.map = map)}
								modules={["coordSystem.geo", "geocode", "util.bounds"]}
							></Map>
						</YMaps>
						<div className="text-danger" style={{ display: this.state.addressError }}>
							Address must be entered.
						</div>
						<div className="text-danger" style={{ display: this.state.addressNotFoundError }}>
							Sorry. Address not found. Try different one.
						</div>
					</div>

					<div className="control-group">
						<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Choose payment method</label>
						</div>
						<div class="container">
							<div class="row">
								{this.state.isPaypalAllowed === true &&
									<div class="col">

										<button type="button" class="btn  btn-sm" data-toggle="button" aria-pressed="false" autocomplete="off"
											onClick={this.handlePayPalClicked}>
											<img src={paypal} className="App-logo" alt="logo" />
										</button>

									</div>
								}
								{this.state.isBankCardAllowed === true &&
									<div class="col">
										<button type="button" class="btn  btn-sm" data-toggle="button" aria-pressed="false" autocomplete="off"
											onClick={(e) => this.handleBankCardClicked(e)}>
											<img src={bank_cards} className="App-logo" alt="logo" />
										</button>
									</div>
								}
								{this.state.isQRAllowed === true &&
									<div class="col">

										<button type="button" class="btn  btn-sm" data-toggle="button" aria-pressed="false" autocomplete="off">
											<img src={qr} className="App-logo" alt="logo" />
										</button>

									</div>
								}
								{this.state.isBitcoinAllowed === true &&
									<div class="col">

										<button type="button" class="btn  btn-sm" data-toggle="button" aria-pressed="false" autocomplete="off">
											<img src={bitcoin} className="App-logo" alt="logo" />
										</button>

									</div>
								}
							</div>
							<div className="App">
								{this.props.clientSecret && this.state.cardSelected && (
									<Elements options={this.props.options} stripe={stripePromise}>
										<CheckoutForm
											handleBankCard={this.handleBankCard}
											 />
									</Elements>
								)}
							</div>
						</div>
					</div>

				</Modal.Body>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully placed an order."
				/>
			</Modal>

		);
	}
}

export default Address;
