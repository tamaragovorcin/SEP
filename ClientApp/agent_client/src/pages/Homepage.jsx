import React from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { Link } from "react-router-dom";

class HomePage extends React.Component {
	hasRole = (reqRole) => {
		
		
		let roles =  ""
		if (localStorage === null) return false;

		roles = localStorage.getItem("keyRole").substring(1, localStorage.getItem('keyRole').length-1)
		
		if (roles === null) return false;

		if (reqRole === "*") return true;

		
		if (roles.trim() === reqRole) 
		{
			
			return true;
		}
		return false;
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<section id="hero" className="d-flex align-items-center">
					<div className="container" >
						<h1 >Welcome to Apeiron</h1>
						<Link hidden={this.hasRole("*")} to="/registration" className="btn-get-started scrollto">
							Register
						</Link>
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
