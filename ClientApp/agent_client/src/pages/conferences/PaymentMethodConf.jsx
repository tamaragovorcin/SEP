import React, { Component } from "react";
import Axios from "axios";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT } from "../../constants.js";
import getAuthHeader from "../../GetHeader";

class PaymentMethodConf extends Component {
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

export default PaymentMethodConf;
