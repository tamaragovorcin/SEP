import React, { Component } from "react";
import Axios from "axios";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";


class Login extends Component {
	state = {
	
		username: "",
		password: "",
		redirect: false,
		usernameError: "none",
		passwordError: "none",

	};
	handleUsernameChange = (event) => {
		this.setState({ username: event.target.value });
	};
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

	handleLogin = () => {
		this.setState({ emailError: "none", passwordError: "none" });

		if (this.validateForm()) {
			let loginDTO = { username: this.state.username, password: this.state.password };
			Axios.post( "http://localhost:9090/auth-service/api/webshop/login", loginDTO)
				.then((res) => {
					if (res.status === 401) {
						alert("Bad credentials!")
					} else if (res.status === 500) {
						alert("Server error!")

					} else {
						localStorage.setItem("webShopId", JSON.stringify(res.data));

						this.setState({ redirect: true });
					}
				})
				.catch ((err) => {
					alert("Bad credentials!")
					console.log(err);
		});
	}
};

validateForm = () => {
	if (this.state.username === "") {
		this.setState({ username: "inline" });
		return false;
	} else if (this.state.password === "") {
		this.setState({ passwordError: "inline" });
		return false;
	}

	return true;
};

render() {
	if (this.state.redirect) return <Redirect push to="/definePaymentMethods" />;
	return (
		<React.Fragment>
			<div className="container" style={{ marginTop: "10%" }}>
			
				<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
					Login
					</h5>

				<div className="row section-design">
					<div className="col-lg-8 mx-auto">
						<br />
						<form id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
								<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
									<input
										placeholder="Username"
										className="form-control"
										id="name"
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
									<input
										placeholder="Password"
										className="form-control"
										id="password"
										type="password"
										onChange={this.handlePasswordChange}
										value={this.state.password}
									/>
								</div>
								<div className="text-danger" style={{ display: this.state.passwordError }}>
									Password must be entered.
									</div>
							</div>

							<div className="form-group">
								<Button
									style={{ background: "#1977cc", marginTop: "15px", marginLeft: "40%", width: "20%" }}
									onClick={this.handleLogin}
									className="btn btn-primary btn-xl"
									id="sendMessageButton"
									type="button"
								>
									Login
									</Button>
							</div>
						</form>
					</div>
				</div>
			</div>
			
		</React.Fragment>
	);
	}
}

export default Login;