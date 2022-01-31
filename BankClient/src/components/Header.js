import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";


class Header extends Component {
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
			<header id="header" className="fixed-top">
				<div className="container d-flex align-items-center">
					<h4 className="mr-auto">
							{this.state.banks.map((bank) => (
								<u><Link 
								style={{marginLeft:"10px"}}
								onClick={() => this.handleClickOnBank(bank.id)}
								>
								{bank.name}	
								</Link></u>
								
							))}
					</h4>

					<nav className="nav-menu d-none d-lg-block">
						<ul>
							<li className="active">
								<Link to="/">Home</Link>
							</li>
							<li className="active"  hidden={!this.hasRole("HEAD_OF_PROCUREMENT")}>
								<a href="http://localhost:3001/#/login">Equipment ordering</a>
							</li>
							<li className="active"  hidden={!this.hasRole("GENERAL_SERVICE")}>
								<a href="http://localhost:3001/#/login">Courses/Conferences</a>
							</li>
							
						</ul>
					</nav>
				</div>
			</header>
		);
	}
}

export default Header;
