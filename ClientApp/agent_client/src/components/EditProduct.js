import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import getAuthHeader from "../GetHeader";
import Axios from "axios";
import { BASE_URL_AGENT } from "../constants.js";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from 'react-responsive-carousel';

import ModalDialog from "./ModalDialog";

class EndEntityCreateModal extends Component {
	state = {
		quantity: "",
		openModal: false,
		quantityError: "none",

	};
	componentDidMount() {
	}
	handleModalClose = ()=>{
		this.setState({openModal: false})
		window.location.reload();
	}
	handleQuantityChange = (event) => {

        this.setState({ quantity: event.target.value });
	
    };
	handleEditChange = () => {
	
		

		let newOrderDTO = {
			productId: this.props.product.id,
			price: this.props.price,
			quantity: this.props.quantity,
			name: this.props.name

		};


	

			Axios.post(BASE_URL_AGENT + "/api/product/edit", newOrderDTO, {  headers: { Authorization: getAuthHeader() } })
				.then((res) => {
					if (res.status === 500) {
						this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
					} else {
						this.setState({openModal: true})
						/*this.setState({
							hiddenSuccessAlert: false,
							successHeader: "Success",
							successMessage: "You successfully sent a reservation.",
							hiddenEditInfo: true,
						});*/
						
					}
				})
				.catch((err) => {
					console.log(err);
				});
		
	

	}


	render() {

		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-150h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>
				
					
					<div className="control-group">
					<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Name:</label>
							<input
								placeholder="Quantity"
								class="form-control"
								type="text"
								id="name"
								onChange={this.props.handleNameChange}
								value={this.props.name}
							/>
						</div>

						<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Quantity:</label>
							<input
								placeholder="Quantity"
								class="form-control"
								type="text"
								id="name"
								onChange={this.props.handleQuantityChange}
								value={this.props.quantity}
							/>
						</div>

						<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Price:</label>
							<input
								placeholder="Price"
								class="form-control"
								type="text"
								id="name"
								onChange={this.props.handlePriceChange}
								value={this.props.price}
							/>
						</div>

					</div>

			

					<div style={{ marginTop: "2rem", marginLeft: "12rem" }}>
						<Button className="mt-3" onClick={this.handleEditChange}>
							{this.props.buttonName}
						</Button>
					</div>
				</Modal.Body>
				<ModalDialog
                    show={this.state.openModal}
                    onCloseModal={this.handleModalClose}
                    header="Success"
                    text="You have successfully edited product."
                />
			</Modal>
			
		);
	}
}

export default EndEntityCreateModal;
