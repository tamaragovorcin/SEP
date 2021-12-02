import React, { Component } from "react";
import TopBar from "../components/TopBar";
import Header from "../components/Header";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import "../App.js";
import { Redirect } from "react-router-dom";
import Order from "../components/Order";

import ModalDialog from "../components/ModalDialog";

class All extends Component {
	state = {
		tshirts: [],
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
		shirt: "",
		selectedColor: "",
		showedColors: [],
		colors: [],
		selectedSize: "",
		showedSizes: [],
		sizes: [],
		openModal: false,


	};

	hasRole = (reqRole) => {
        let roles = JSON.parse(localStorage.getItem("keyRole"));

        if (roles === null) return false;

        if (reqRole === "*") return true;

        for (let role of roles) {
            if (role === reqRole) return true;
        }
        return false;
    };

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};
	handleModalClose = () => {
		this.setState({ openModal: false });
		window.location.reload();
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
		
		Axios.get(BASE_URL + "/api/product/all")
			.then((res) => {
				this.setState({ tshirts: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

	}

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleDelete = (e,id) => {
		Axios.get(BASE_URL + "/api/product/delete/" + id)
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



	handleClickOnproduct = (id) => {
		this.setState({ shirt: id });
		this.setState({ showOrderModal: true });
		this.setState({ colors: id.colors });

	};

	handleOrderModalClose = () => {
		this.setState({ showOrderModal: false });
	};

	handleColorChange = (e) => {

		let u = [];
		this.setState({ selectedColor: e.target.value }, () => {
			console.log(this.state);
		});
		this.state.colors.forEach((chain) => {
			if (chain.color === e.target.value) {
				u.push(chain.sizes);
			}
		});


		this.state.sizes = u;
		this.setState({ sizes: u });
		this.setState({ showedSizes: u });
		this.changeEndEntityChain(e.target.value);
	};

	changeEndEntityChain = (e) => {
		if (e === "") {
			this.setState({ showedColors: this.state.colors });
		} else {
			let u = [];
			this.state.colors.forEach((chain) => {
				if (chain.color !== e) {
					u.push(chain);
				}
			});

			this.setState({ showedColors: u });
		}
	};

	handleSizeChange = (e) => {
		this.setState({ selectedSize: e.target.value }, () => {
			console.log(this.state);
		});
		this.changeEndEntitySizeChain(e.target.value);
	};

	changeEndEntitySizeChain = (e) => {
		if (e === "") {
			this.setState({ showedSizes: this.state.sizes });
		} else {
			let u = [];
			this.state.sizes.forEach((chain) => {
				if (chain.size !== e) {
					u.push(chain);
				}
			});

			this.setState({ showedSizes: u });
		}
	};

	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">All products</h5>

					
					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.tshirts.map((product) => (
								<tr 
									id={product.id}
									key={product.id}
									style={{ cursor: "pointer" }}
								
								>
									<td width="130em">
										<img className="img-fluid" src={product.pictures?.[0]} width="70em" />
									</td>
									<td>
										<div>
											<b>Name: </b> {product.name}
										</div>
										<div>
											<b>Price: </b> {product.price}
										</div>
										<div>
                                        <b>Quantity: </b> {product.price}
										</div>



									
										<div hidden={!this.hasRole("ROLE_USER")}>  <button
											style={{
												background: "#1977cc",
												marginTop: "15px",
												marginLeft: "40%",
												width: "20%",
											}}
											onClick={() => this.handleClickOnproduct(product)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Add to cart
									</button></div>
									<div hidden={!this.hasRole("ROLE_ADMIN")}>  <button
											style={{
												background: "#1977cc",
												marginTop: "15px",
												marginLeft: "40%",
												width: "20%",
											}}
											onClick={(e)=>this.handleDelete(e,product.id)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Delete
									</button></div>

									</td>
								</tr>
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
				<Order
					buttonName="Add"
					header="Add to cart"
					show={this.state.showOrderModal}
					onCloseModal={this.handleOrderModalClose}
					handleColorChange={this.handleColorChange}
					handleSizeChange={this.handleSizeChange}
					shirt={this.state.shirt}
				/>
			</React.Fragment>
		);
	}
}

export default All;