import React from "react";
import { Link } from "react-router-dom";

class TopBar extends React.Component {

	handleLogout = () => {
		localStorage.removeItem("keyToken");
		localStorage.removeItem("keyRole");
		localStorage.removeItem("expireTime");

	};
	render() {
		return (
			<div id="topbar" className="d-none d-lg-flex align-items-center fixed-top">
			<div className="container d-flex">
				<div className="contact-info mr-auto">
					
				</div>

				<div className="register-login">
				
					<Link to="/login" >
						Login
					</Link>
					<Link onClick={this.handleLogout} to="/login">
						Logout
					</Link>
				</div>
			</div>
		</div>
		);
	}
}

export default TopBar;