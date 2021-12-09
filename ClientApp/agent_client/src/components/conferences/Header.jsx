import React, { Component } from "react";
import { Link } from "react-router-dom";
import { RiAddCircleLine } from 'react-icons/ri';
import { FiTruck } from 'react-icons/fi';
import { VscHome } from 'react-icons/vsc';
import { AiOutlineOrderedList, AiOutlineShoppingCart } from 'react-icons/ai';


import NewConference from "./NewConference";

class Header extends Component {
	
	state = {
		showAddConference : false,
		pictures: [],
		openModal: false,
		textSuccessfulModal : "",
		byteArray : ""
	}

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};
	handleAddConference = () => {
		this.setState({ showAddConference: true });
		this.setState({pictures: []})
	};
	handleNewConferenceModalClose = ()=>{
		this.setState({ showAddConference: false });

	}
	render() {
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<header id="header" className="fixed-top">
				<div className="container d-flex align-items-center">
					<h1 className="logo mr-auto">
						<Link to="/">Conferences/courses web shop</Link>
					</h1>

					<nav className="nav-menu d-none d-lg-block">
						<ul>
						<li  hidden={!this.hasRole("*")}>
								<Link to="/conferences"><VscHome /></Link>
							</li>
							<li  hidden={!this.hasRole("ROLE_ADMIN")}>
							<button  onClick={this.handleAddConference} className="btn btn-outline-secondary btn-sm" style={{  border: "none", marginBottom: "1rem" }}><RiAddCircleLine /></button>
							</li>
							<li>
							<Link to="/conferencesCart"><AiOutlineShoppingCart /></Link>
							</li>
							<li>
							<Link to="/conferencesOrders"><FiTruck /></Link>
							</li>
						
							
							
						</ul>
					</nav>
					<NewConference
						show={this.state.showAddConference}
						onCloseModal={this.handleNewConferenceModalClose}
						header="Add new conference"
						hiddenOne = {this.state.hiddenOne}
						noPicture = {this.state.noPicture}
						onDrop = {this.onDrop}

					/>
				</div>
			</header>
		);
	}
}

export default Header;