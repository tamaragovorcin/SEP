import React, { Component } from "react";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT } from "../../constants.js";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import ModalDialog from "../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import { Carousel } from 'react-responsive-carousel';
import { YMaps, Map } from "react-yandex-maps";

class ConferenceOrders extends Component {
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


	};

	hasRole = (reqRole) => {


		let roles = ""
		if (localStorage === null) return false;

		roles = localStorage.getItem("keyRole").substring(1, localStorage.getItem('keyRole').length - 1)

		if (roles === null) return false;

		if (reqRole === "*") return true;


		if (roles.trim() === reqRole) {

			return true;
		}
		return false;
	};

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
	handleOrderModalClose = () => {
		this.setState({ showOrderModal: false });
	};
	componentDidMount() {
		let id = localStorage.getItem("userId").substring(1, localStorage.getItem('userId').length - 1);
		Axios.get(BASE_URL_AGENT + "/api/conference/userPurchases", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ products: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

	}

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleDelete = (e, id) => {
		Axios.get(BASE_URL_AGENT + "/api/cart/remove/" + id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {

				this.setState({ openModal: true });
				window.location.reload();
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

	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">My orders</h5>


					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.products.map((p) => (
								<div>
									<p><label>Status: {p.status} </label></p>
									<p><label >   Date of reservation: {p.dateOfReservation} </label></p>
									
									<p><label>User: {p.registeredUserId} </label></p>
									{p.items.map((item) => (
								<tr
									id={p.id}
									key={p.id}
									style={{ cursor: "pointer" }}

								>
								
									<td width="130em">
									<img className="img-fluid" src={item.pictures?.[0]} width="70em" />
									</td>
									<td>
										<div>
											<b>{item.conferenceName}</b> 
										</div>
										<div>
											<b>Accommodation: </b> {item.accommodation}
										</div>
										<div>
											<b>Transportation: </b> {item.transportation}
										</div>
										<div>
											<b>Price: </b> {item.price}&nbsp; €
										</div>
										<div>
											<b>Quantity: </b> {item.quantity}
										</div>





									</td>
								
								</tr>
									))}
										<label><b>____________________________________________________________________________________________________________________________</b></label>
							
									</div>

								))}
						</tbody>
					</table>



				</div>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully removed the item."
				/>

			
			</React.Fragment>
		);
	}
}

export default ConferenceOrders;

