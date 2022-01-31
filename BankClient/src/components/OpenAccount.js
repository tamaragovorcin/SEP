import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";


class OpenAccount extends Component {
	state = {
		banks: [],
		redirect: false,
		redirectUrl : ""

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
	

	handleClickOnBank= (id) => {
		this.setState({
			redirect: true,
			redirectUrl: "/bank/" + id,
		});
	};
	componentDidMount() {
		
		Axios.get(BASE_URL + "/api/bank/all")
			.then((res) => {
				this.setState({ banks: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

	}
	render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
            <React.Fragment>


<div id="center">
        <div className="container d-flex align-items-center" style={{ marginTop: "10%" }}>
					<div className="row section-design">
						<div>
							<br />
							<form id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Your name:</label>
										<input
											placeholder="Ime"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Ime je obavezno polje.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label> UCIN(Your unique citizens number):</label>
										<input
											placeholder="Prezime"
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleSurnameChange}
											value={this.state.surname}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Prezime je obavezno polje.
									</div>
								</div>
							
                                
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Broj telefona:</label>
										<input
											placeholder="+387..."
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Broj telefona je obavezno polje.
									</div>
								</div>
								<div className="control-group">
									<label>Broj licne karte:</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											class="form-control"
											type="text"
											onChange={this.handleIdCardNumberChange}
											value={this.state.idCardNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.idCardNumberError }}>
										Broj licne karte je obavezno polje.
									</div>
								</div>
								
								<div className="form-group">
									<button
									
										onClick={this.handleSignUp}
										className="btn btn-info btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Save
									</button>
								</div>
							</form>
						</div>
                        </div>

        </div>
      

        
    </div>
            </React.Fragment>

		);
	}
}

export default OpenAccount;
