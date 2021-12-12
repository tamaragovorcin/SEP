import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import Axios from "axios";
import { BASE_URL_AGENT, BASE_URL_USER } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import HeadingAlert from "../components/HeadingAlert";
import getAuthHeader from "../GetHeader";

import ModalDialog from "../components/ModalDialog";
class Login extends Component {
	state = {
		errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		payment: [],
		card: false,
		bitcoin: false,
		paypal:false,
		qr:false,
		password: "",
		redirect: false,
		emailError: "none",
		passwordError: "none",
		openModal:false

	};
	handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handleModalClose = ()=>{
		this.setState({openModal: false})
	}
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
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

	componentDidMount() {
		Axios.get(BASE_URL_AGENT + "/api/payment/all", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				res.data.methods.forEach(element => {
					if(element === "Card"){

						this.setState({ card: true });
					}
					if(element === "Paypal"){

						this.setState({ paypal: true });
					}
					if(element === "Bitcoin"){

						this.setState({ bitcoin: true });
					}
					if(element === "Qr"){

						this.setState({ qr: true });
					}
				});
					
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

	}

	handlePaymentChange = () => {


		let pom = [];
	
		this.setState({ payment: pom });
		if (this.state.card){
			pom.push("Card")
		}
		if(this.state.bitcoin){
			pom.push("Bitcoin")
		}
		if(this.state.paypal){
			pom.push("Paypal")
		}
		if(this.state.qr){
			pom.push("Qr")
		}
		

		console.log(pom)
		this.setState({ payment: pom });

		let paymentDTO = {
			methods: pom
		}
		Axios.post(BASE_URL_AGENT + "/api/payment/add"  ,paymentDTO, { headers: { Authorization: getAuthHeader() } })
		.then((res) => {

			this.setState({ openModal: true });
		})
		.catch((err) => {
			console.log(err);
		});

	}
	handlePasswordChangeCard = (event) => {
		if(this.state.card === true){
			
		this.setState({ card: false });
		} else{
			
		this.setState({ card: true });
		}
	};
	handlePasswordChangePaypal = (event) => {
		if(this.state.paypal === true){
			
			this.setState({ paypal: false });
			} else{
				
			this.setState({ paypal: true });
			}
	};

	handlePasswordChangeBitcoin = (event) => {
		if(this.state.bitcoin === true){
			
			this.setState({ bitcoin: false });
			} else{
				
			this.setState({ bitcoin: true });
			}
	};

	handlePasswordChangeQr = (event) => {
		if(this.state.qr === true){
			
			this.setState({ qr: false });
			} else{
				
			this.setState({ qr: true });
			}
	};
	


	validateForm = () => {
		if (this.state.email === "") {
			this.setState({ emailError: "inline" });
			return false;
		} else if (this.state.password === "") {
			this.setState({ passwordError: "inline" });
			return false;
		}

		return true;
	};


	render() {
		if (this.state.redirect) return <Redirect push to="/" />;
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<HeadingAlert
						hidden={this.state.hiddenErrorAlert}
						header={this.state.errorHeader}
						message={this.state.errorMessage}
						handleCloseAlert={this.handleCloseAlert}
					/>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Choose payment method that this webshop accepts.
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" noValidate="novalidate">
								<div style={{ color: "#6c757d", opacity: 1 }}>
									<p><input type="checkbox"   checked={this.state.card === true ? true : false} value="Card" name="method" onChange={(e) => this.handlePasswordChangeCard(e)} /> Card</p>
									<p><input type="checkbox" checked={this.state.paypal === true ? true : false}  value="Paypal" name="method" onChange={(e) => this.handlePasswordChangePaypal(e)} /> Paypal</p>
									<p><input type="checkbox" checked={this.state.bitcoin === true ? true : false}  value="Bitcoin" name="method" onChange={(e) => this.handlePasswordChangeBitcoin(e)} /> Bitcoin </p>
									<p><input type="checkbox" checked={this.state.qr === true ? true : false}  value="QR" name="method" onChange={(e) => this.handlePasswordChangeQr(e)} /> QR </p>
								</div>

							

								<div className="form-group">
									<Button
										style={{ background: "#1977cc", marginTop: "15px", marginLeft: "40%", width: "20%" }}
										onClick={this.handlePaymentChange}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Save
									</Button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully changed payment methods."
				/>

			</React.Fragment>
		);
	}
}

export default Login;
