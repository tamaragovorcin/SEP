import React from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { Link } from "react-router-dom";

class HomePage extends React.Component {
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
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<section id="hero" className="d-flex align-items-center">
					<div className="container">
						<h1>Welcome</h1>
					
					</div>
					
				</section>

				<a href="#" className="back-to-top">
					<i className="icofont-simple-up"></i>
				</a>
			</React.Fragment>
		);
	}
}

export default HomePage;
