import React from "react";
import { Link } from "react-router-dom";

import Axios from "axios";
import { FiTruck } from 'react-icons/fi';
import { VscHome } from 'react-icons/vsc';
import { RiAddCircleLine } from 'react-icons/ri';
import { AiOutlineOrderedList, AiOutlineShoppingCart } from 'react-icons/ai';
import { BASE_URL_AGENT } from "../constants.js";
import { GiPodiumWinner } from 'react-icons/gi';
import {MdPayment} from 'react-icons/md'

import AddPostModal from "../components/Posts/AddPostModal";

import Select from 'react-select';
import ModalDialog from "../components/ModalDialog";
import getAuthHeader from "../GetHeader";
class Header extends React.Component {

	state = {
		search: "",
		users: [],
		options: [],
		optionDTO: { value: "", label: "" },
		userId: "",
		showImageModal : false,
		pictures: [],
		openModal: false,
		hiddenOne: true,
		hiddenMultiple: true,
		showTopCampaignsModal :false,
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
	handlePostModalClose = () => {
		this.setState({ showImageModal: false });
	};
	handlePostModalOpen = () => {
		this.setState({ showImageModal: true });
		this.setState({pictures: []})
	};

	hadleGetTopCampaignsModalOpen = () => {
		this.setState({ showTopCampaignsModal: true });
	};
	hadleGetTopCampaignsModalClose = () =>{
		this.setState({ showTopCampaignsModal: false });

	}
	handleSearchChange = (event) => {
		this.setState({ search: event.target.value });
	};

	handleChange = (event) => {
		this.setState({ userId: event.value });
		window.location = "#/followerProfilePage/" + event.value;

	};

	hadleGetTopCampaigns = ()=> {
		const FileDownload = require("js-file-download");

		Axios.get(BASE_URL_AGENT + "/api/bestCampaigns/" + this.state.token, {  headers: { Authorization: getAuthHeader() },responseType: 'blob' })
			.then((response) => {
				if (response.status === 500) {
					alert("Token nije dobar!")
				} else{
			
				this.setState({byteArray : response.data})
				FileDownload(response.data, "report.pdf");
				
				this.setState({ openModal: true });
				this.setState({ textSuccessfulModal: "Top campaigns are successfully stored" });}
			})
			.catch((err) => {
				this.setState({ openModal: true });
				this.setState({ textSuccessfulModal: "Top campaigns can not be stored" });
				console.log(err);
			});

	}

	handleTokenChange = (event) => {
		this.setState({ token: event.target.value });
	};
	handleModalClose = () => {
		this.setState({ openModal: false });
	};

	render() {


		return (
			<React.Fragment>
			<header id="header" className="fixed-top">
				<div className="container d-flex align-items-center">
					<label className="logo mr-auto" style={{ fontFamily: "Trattatello, fantasy" }}>
						<Link to="/">Web shop</Link>
					</label>
					
					<nav className="nav-menu d-none d-lg-block">
						<ul>
						<li  hidden={!this.hasRole("*")}>
								<Link to="/"><VscHome /></Link>
							</li>
							<li  hidden={!this.hasRole("ROLE_ADMIN")}>
							<button  onClick={this.handlePostModalOpen} className="btn btn-outline-secondary btn-sm" style={{  border: "none", marginBottom: "1rem" }}><RiAddCircleLine /></button>
							</li>
			
							<li  hidden={!this.hasRole("ROLE_USER")}>
							<Link to="/orders"><AiOutlineShoppingCart /></Link>
							</li>
							<li  hidden={!this.hasRole("ROLE_USER")}>
							<Link to="/ordered"><FiTruck /></Link>
							</li>
							<li  hidden={!this.hasRole("ROLE_ADMIN")}>
							<Link to="/orderedAdmin"><FiTruck /></Link>
							</li>
							<li  hidden={!this.hasRole("ROLE_ADMIN")}>
							<Link to="/paymentMethod"><MdPayment /></Link>
							</li>
							
			

							
						</ul>
					</nav>
				</div>
			</header>







			<AddPostModal
						show={this.state.showImageModal}
						onCloseModal={this.handlePostModalClose}
						header="Add new product"
						hiddenMultiple = {this.state.hiddenMultiple}
						hiddenOne = {this.state.hiddenOne}
						noPicture = {this.state.noPicture}
						onDrop = {this.onDrop}

						handleAddFeedPost = {this.handleAddFeedPost}
						handleAddStoryPost = {this.handleAddStoryPost}
						handleAddStoryPostCloseFriends = {this.handleAddStoryPostCloseFriends}
						handleAddFeedPostAlbum = {this.handleAddFeedPostAlbum}
						handleAddStoryPostAlbum= {this.handleAddStoryPostAlbum}
						handleAddStoryPostAlbumCloseFriends = {this.handleAddStoryPostAlbumCloseFriends}
						addressNotFoundError = {this.state.addressNotFoundError}
						handleDescriptionChange = {this.handleDescriptionChange}
						handleHashtagsChange = {this.handleHashtagsChange}
						handleAddTagsModal = {this.handleAddTagsModal}


					/>




			<ModalDialog
						show={this.state.openModal}
						onCloseModal={this.handleModalClose}
						header="Successful"
						text={this.state.textSuccessfulModal}
						openModal = {this.state.openModal}
					/>


			

		
			</React.Fragment>

);


	}
}

export default Header;