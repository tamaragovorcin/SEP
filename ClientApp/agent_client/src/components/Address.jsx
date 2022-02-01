import React, { Component } from "react";
import { Modal } from "react-bootstrap";
import Axios from "axios";
import { BASE_URL_PAYPAL, BASE_URL_BITCOIN, BASE_URL_AGENT } from "../constants.js";
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
		isPaypalAllowed: false,
		isBankCardAllowed: false,
		isQRAllowed: false,
		isBitcoinAllowed: false,
		clentSecret: "",
		options: {},
		cardSelected: false,
	};



	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
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
	

	handleQuantityChange = (event) => {

		this.setState({ quantity: event.target.value });

	};

	getPrice = (products) => {
		let price = 0;
		for (let i = 0; i < products.length; i++) {
			price = price + products[i].quantity * products[i].price
		}
		return price
	}

	startPayment = () => {
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
						let totalPrice = this.getPrice(products)

						document.cookie = "totalPrice=" + totalPrice + ";path=/";
						document.cookie = "webShopId=" + 2 + ";path=/";
						document.cookie = "webshopType=" + "product" + ";path=/";
						const purchaseDTO = {
							address : foundAddress,
							products : products,
						}
						Axios.post(BASE_URL_AGENT + "/api/purchase/add", purchaseDTO, {  headers: { Authorization: getAuthHeader() } })
						.then((res) => {
							let paymentId = res.data;
							document.cookie = "paymentId=" + paymentId + ";path=/";

							window.location.href = 'http://localhost:3000/'
						})
						.catch((err) => {
							console.log(err);
						});		

						
					}
				}
			});
	}

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


				<div >
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
						<button
							style={{
								background: "#1977cc",
								width: "50%",
							}} 
							type="button" class="btn  btn-sm" 
							className="btn btn-primary btn-xl"
							onClick={()=> this.startPayment()}> 
							Choose payment method
						</button>
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
