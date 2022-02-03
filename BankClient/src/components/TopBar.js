import React from "react";
import { Link } from "react-router-dom";

class TopBar extends React.Component {
	handleLogout = () => {
		localStorage.removeItem("keyToken");
		localStorage.removeItem("keyRole");
		localStorage.removeItem("expireTime");
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
		const myStyle = {
			margin: 10,
		};
		return (
			<div id="topbar" className="d-none d-lg-flex align-items-center fixed-top">
				<div className="container d-flex">
					<div className="contact-info mr-auto">
						<i className="icofont-envelope"></i>{" "}
						<a style={myStyle} href="mailto:quinceit.pquince@gmail.com">
							bankingSystem@gmail.com
						</a>
						<a href="#" style={myStyle} className="instagram">
							<i className="icofont-instagram"></i>Banking System
						</a>
						<a href="#" style={myStyle} className="facebook">
							<i className="icofont-facebook"></i>Banking System
						</a>
					</div>

					<div className="register-login">
						<Link to="/registration" hidden={this.hasRole("*")}>
							Register
						</Link>
						<Link to="/login" hidden={this.hasRole("*")}>
							Login
						</Link>
						<Link onClick={this.handleLogout} to="/login" hidden={!this.hasRole("*")}>
							Logout
						</Link>
						
					</div>
				</div>
			</div>
		);
	}
}

export default TopBar;
