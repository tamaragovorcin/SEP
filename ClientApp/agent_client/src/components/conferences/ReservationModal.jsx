import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import "react-datepicker/dist/react-datepicker.css";
import getAuthHeader from "../../GetHeader";
import Axios from "axios";
import { BASE_URL_AGENT } from "../../constants.js";
import "react-responsive-carousel/lib/styles/carousel.min.css"; 
import { Carousel } from 'react-responsive-carousel';
import ModalDialog from "../../components/ModalDialog";

class ReservationModal extends Component {
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
	handleReserveChange = () => {
		if(parseInt(this.state.quantity , 10 ) + 1 > parseInt(this.props.conference.capacity , 10 ) + 1 ){
			this.setState({ quantityError: "initial" });
		}else{

			let id = localStorage.getItem("userId").substring(1, localStorage.getItem('userId').length - 1);
		let dto = {
			registeredUserId: id,
			conferenceId: this.props.conference.id,
			quantity: this.state.quantity

		};
		Axios.post(BASE_URL_AGENT + "/api/conference/addToCart", dto, {  headers: { Authorization: getAuthHeader() } })
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
						this.props.onCloseModal()
						
					}
				})
				.catch((err) => {
					console.log(err);
				});

	}

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
					<div>
							<Carousel dynamicHeight={true}>
								{this.props.conference.pictures?.map(img => (<div>
									<img src={img} />
								</div>))}

							</Carousel>
						</div>

						<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
							<label>Number of people:</label>
							<input
								placeholder="Quantity"
								class="form-control"
								type="number"
								id="name"
								onChange={this.handleQuantityChange}
								value={this.state.quantity}
							/>
						</div>

					</div>
					<div className="text-danger" style={{ display: this.state.quantityError }}>
										Quantity must be less than available.
									</div>
					

					<div style={{ marginTop: "2rem", marginLeft: "12rem" }}>
						<Button className="mt-3" onClick={this.handleReserveChange}>
							{this.props.buttonName}
						</Button>
					</div>
				</Modal.Body>
				<ModalDialog
                    show={this.state.openModal}
                    onCloseModal={this.handleModalClose}
                    header="Success"
                    text="You have successfully added reservation to cart."
                />
			</Modal>
			
		);
	}
}

export default ReservationModal;
