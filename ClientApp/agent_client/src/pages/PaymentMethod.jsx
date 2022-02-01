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


	render() {
		if (this.state.redirect) return <Redirect push to="/" />;
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<p>Id of your webshop is : 2</p>
					<p><a href="http://localhost:3000/#/register">Register on payment service provider</a></p>
					<p><a href="http://localhost:3000/#/login">Log in on payment service provider</a></p>

				</div>
				

			</React.Fragment>
		);
	}
}

export default Login;
