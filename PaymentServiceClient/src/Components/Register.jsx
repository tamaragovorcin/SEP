import React, { Component } from "react";
import Axios from "axios";
import { Redirect } from "react-router-dom";

class RegisterPage extends Component {
	state = {
		username: "",
		password: "",
		currency : "dinar",
		repeatPassword: "",
		passwordError: "none",
		repeatPasswordError: "none",
		repeatPasswordSameError: "none",
		successUrl : "",
		failureUrl : "",
		errorUrl : "",
		redirect : false,
		webShopId : "",
		usernameError : "none",
		successUrlError : "none",
		failureUrlError : "none",
		errorUrlError : "none",
		webShopIdError : "none"
	};

	constructor(props) {
		super(props);
	}

	handleUsernameChange = (event) => {
		this.setState({ username: event.target.value });
	};

	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};
	
	handleRepeatPasswordChange = (event) => {
		this.setState({ repeatPassword: event.target.value });
	};
	handleSuccessUrlChange = (event) => {
		this.setState({ successUrl: event.target.value });
	};
	handleFailureUrlChange = (event) => {
		this.setState({ failureUrl: event.target.value });
	};
	handleErrorUrlChange = (event) => {
		this.setState({ errorUrl: event.target.value });
	};

	handleWebShopIdChange = (event) => {
		this.setState({ webShopId: event.target.value });
	};

	handleSignUp = () => {
		const completeDTO = {
			username : this.state.username,
			password : this.state.password,
			successUrl : this.state.successUrl,
			errorUrl : this.state.errorUrl,
			failureUrl : this.state.failureUrl,
			webShopId : this.state.webShopId,
			currency : this.state.currency
		}
		Axios.post("http://localhost:9090/auth-service/api/webshop/signup", completeDTO)
		.then((res) => {
			if (res.status === 500) {
				alert("ERROR with registration!")
			} else {
				this.setState({ redirect: true })
			}
		})
		.catch((err) => {
			alert("ERROR with registration!")
			console.log(err);
		});

	};
	handleCurrencyChange = (e)=>{
		this.setState({ currency: e.target.value });

	}

	render() {
		if (this.state.redirect) return <Redirect push to="/login" />;

		return (
			<React.Fragment>

				<div className="container" style={{ marginTop: "10%" }}>
					
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Registration
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" noValidate="novalidate">
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Username:</label>
										<input
											placeholder="username"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleUsernameChange}
											value={this.state.username}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.usernameError }}>
										Username must be entered.
									</div>
								</div>

								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Success url:</label>
										<input
											placeholder="Success url"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleSuccessUrlChange}
											value={this.state.successUrl}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.successUrlError }}>
										Success url must be entered.
									</div>
								</div>

								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Failure url:</label>
										<input
											placeholder="Failure url"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleFailureUrlChange}
											value={this.state.failureUrl}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.failureUrlError }}>
										Failure url must be entered.
									</div>
								</div>

								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Error url:</label>
										<input
											placeholder="Error url"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleErrorUrlChange}
											value={this.state.errorUrl}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.errorUrlError }}>
										Error url must be entered.
									</div>
								</div>

								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>
											Web shop id:
										</label>
										<input
											placeholder="Web shop id"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleWebShopIdChange}
											value={this.state.webShopId}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.webShopIdError }}>
										Web shop id must be entered.
									</div>
								</div>
								<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Currency:</label>
											<select className='form-control'
										value={this.state.currency}
										onChange={this.handleCurrencyChange}>
										<option value="dolar">$</option>
										<option value="dinar">DIN</option>
										<option value="euro">€</option>
									
										</select>
										</div>
									</div>
								<div className="control-group">
									<label>Password:</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											placeholder="Password"
											class="form-control"
											type="password"
											onChange={this.handlePasswordChange}
											value={this.state.password}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.passwordError }}>
										Password must be entered.
									</div>
								</div>
								<div className="control-group">
									<label>Repeat password:</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											placeholder="Repeat password"
											class="form-control"
											type="password"
											onChange={this.handleRepeatPasswordChange}
											value={this.state.repeatPassword}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.repeatPasswordError }}>
										Repeat password must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.repeatPasswordSameError }}>
										Passwords are not the same.
									</div>
								</div>

								<div className="form-group">
									<button
										style={{
											background: "#1977cc",
											marginTop: "15px",
											marginLeft: "40%",
											width: "20%",
										}}
										onClick={this.handleSignUp}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Sign Up
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
			</React.Fragment>
		);
	}
}

export default RegisterPage;