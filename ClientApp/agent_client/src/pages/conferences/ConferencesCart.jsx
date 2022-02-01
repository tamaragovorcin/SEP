import React, { Component } from "react";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT, BASE_URL_PAYPAL, BASE_URL_BITCOIN } from "../../constants.js";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import ModalDialog from "../../components/ModalDialog";
import { withRouter } from 'react-router-dom';
import { Redirect } from "react-router-dom";
import { Carousel } from 'react-responsive-carousel';
import { YMaps, Map } from "react-yandex-maps";
import qr from '../../static/qr.png';
import bank_cards from '../../static/bank_cards.jpg';
import bitcoin from '../../static/bitcoin.png';
import paypal from '../../static/paypall.png';

class ConferencesCart extends Component {


	state = {
		products: [],
		formShowed: false,
		name: "",
		city: "",
		gradeFrom: "",
		gradeTo: "",
		distanceFrom: "",
		distanceTo: "",
		showingSearched: false,
		showingSorted: false,
		currentLatitude: null,
		currentLongitude: null,
		sortIndicator: 0,
		redirect: false,
		redirectUrl: "",
		showOrderModal: false,
		handleOrderModalClose: false,
		openModal: false,
		show: true,
		accommodation : "",
		transportation : "",
		isPaypalAllowed : false,
		isBankCardAllowed : false,
		isQRAllowed : false,
		isBitcoinAllowed : false
	


	};

	hasRole = (reqRole) => {
		
		
		let roles =  ""
		if (localStorage === null) return false;

		roles = localStorage.getItem("keyRole").substring(1, localStorage.getItem('keyRole').length-1)
		
		if (roles === null) return false;

		if (reqRole === "*") return true;

		
		if (roles.trim() === reqRole) 
		{
			
			return true;
		}
		return false;
	};
	handleModalClose = ()=>{
		this.setState({openModal: false})
		window.location.reload();
	}
	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	getCurrentCoords = () => {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition((position) => {
				this.setState({
					currentLatitude: position.coords.latitude,
					currentLongitude: position.coords.longitude,
				});
			});
		}
	};
   
	componentDidMount() {
		let id = localStorage.getItem("userId").substring(1, localStorage.getItem('userId').length - 1);
		Axios.get(BASE_URL_AGENT + "/api/conference/reservations" , {  headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ products: res.data });

				this.setState({accommodation : res.data.accommodation})
				this.setState({transportation : res.data.transportation})
				
				console.log(res.data);
				
				if(res.data.length === 0){
					this.setState({ show: false });
				}
			})
			.catch((err) => {
				console.log(err);
			});


	}

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleDelete = (e, id) => {
		Axios.get(BASE_URL_AGENT + "/api/conference/removeReservation/" + id, {  headers: { Authorization: getAuthHeader() } })
			.then((res) => {

				this.setState({ openModal: true });
				
			})
			.catch((err) => {
				console.log(err);
			});

	};

	handleGradeFromChange = (event) => {
		if (event.target.value < 0) this.setState({ gradeFrom: 0 });
		else this.setState({ gradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		if (event.target.value > 5) this.setState({ gradeTo: 5 });
		else this.setState({ gradeTo: event.target.value });
	};

	handleDistanceFromChange = (event) => {
		this.setState({ distanceFrom: event.target.value });
	};

	handleDistanceToChange = (event) => {
		this.setState({ distanceTo: event.target.value });
	};

	handleCityChange = (event) => {
		this.setState({ city: event.target.value });
	};



	handleClickOnPharmacy = (id) => {
		this.setState({ shirt: id });
		this.setState({ showOrderModal: true });
		this.setState({ colors: id.colors });

	};

	handleFindAccommodation =(idConf, idCart)=>{
		this.setState({
			redirect: true,
			redirectUrl: "/conference/" + idConf+"/"+idCart,
		});
	}

	getPrice = (products)=> {
		let price = 0;
		for (let i = 0; i < products.length; i++) {
			price = price + products[i].price
		}
		return price
	}

	startPayment = () => {
		let totalPrice = this.getPrice(this.state.products)
		document.cookie = "totalPrice=" + totalPrice + ";path=/";
		document.cookie = "webShopId=" + 1 + ";path=/";
		document.cookie = "webshopType=" + "conference" + ";path=/";
		const purchaseDTO = {
			address : null,
			items : this.state.products,
		}

		Axios.post(BASE_URL_AGENT + "/api/conference/addOrder", purchaseDTO, {  headers: { Authorization: getAuthHeader() } })
		.then((res) => {
			let paymentId = res.data;
			document.cookie = "paymentId=" + paymentId + ";path=/";

			window.location.href = 'http://localhost:3000/'
		})
		.catch((err) => {
			console.log(err);
		});

						
	}
	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">My cart</h5>


					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem", background:"lightgray" }}>
						<tbody>
							{this.state.products.map((p) => (
								<tr
									id={p.id}
									key={p.id}
									style={{ cursor: "pointer" }}

								>
									<tr>
									<td>
									</td>
									<td>
									Conference:
									</td>
									<td>
										Accommodation:
									</td>
									<td>
										Transportation:
									</td>
									<td>
										Total price:
									</td>
									<td>

									</td>
									<td  hidden={!p.accommodation==""&& !p.transportation==""}></td>


									</tr>
									<tr>
									<td width="130px">
									<img className="img-fluid" src={p.pictures?.[0]} width="70em" />
									</td>
									<td>
                                        <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {p.name}
										</div>
										
                                    </td>
									<td>
                                        <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {p.accommodation}
										</div>
										
                                    </td>
									<td>
                                        <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {p.transportation}
										</div>
										
                                    </td>
									<td>
                                        <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {p.price}&nbsp; €
										</div>
										
                                    </td>
                                    <td>
										   <button
											style={{
												background: "#1977cc",
												width: "100%",
											}}
											onClick={(e) => this.handleDelete(e, p.cartId)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Remove reservation
										</button>

									</td>
                                    <td hidden={!p.accommodation==""&& !p.transportation==""}>
                                    <button
											style={{
												background: "#1977cc",
												width: "100%",
											}}
											onClick={(e) => this.handleFindAccommodation(p.conferenceId,p.cartId)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											 Find accommodation and transportation
										</button>

                                    </td>
</tr>
								</tr>
							))}
						</tbody>
					</table>

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
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully placed order."
				/>

			
			</React.Fragment>
		);
	}
}

export default ConferencesCart;

