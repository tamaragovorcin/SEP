import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import ImageUploader from 'react-images-upload';
import Axios from "axios";
import { BASE_URL_AGENT } from "../../constants.js";
import ModalDialog from "../ModalDialog";
import getAuthHeader from "../../GetHeader";

class AddTransportation extends Component {
	constructor(props) {
		super(props);
		this.state = {
			name: "",
            departure : "",
            price: 0,
            departureAddress : "",
			departureDate : "",
            departureTime : "",
			openModal: false,
			help: [],
			fileUploadOngoing: false
		}
		this.onDrop = this.onDrop.bind(this);

	}
	
	onDrop(picture) {

		this.setState({
			pictures: [],
		});
		this.setState({
			pictures: this.state.pictures.concat(picture),
		});

		let pomoc = picture.length;
		if(pomoc===0) {
			this.setState({
				noPicture: true,
			});
			this.setState({
				showImageModal: false,
			});
		}
		else {
			this.setState({
				noPicture: false,
			});
			this.setState({
				showImageModal: true,
			});
			
		}


	}
    test(pic) {
        this.setState({
            fileUploadOngoing: true
        });

        const fileInput = document.querySelector("#fileInput");
        const formData = new FormData();

        formData.append("file", pic);
        formData.append("test", "StringValueTest");

        const options = {
            method: "POST",
            body: formData

        };
        fetch(BASE_URL_AGENT + "/api/conference/accommodation/uploadImage", options);
    }
	handleAdd(dto) {
		Axios.post(BASE_URL_AGENT + "/api/conference/addTransportation" , dto, {  headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				let confId = res.data;
				this.setState({ textSuccessfulModal: "You have successfully added transportation for conference." });
				this.props.onCloseModal()
			})
			.catch((err) => {
				console.log(err);
			});
		this.setState({ openModal: true });
	}
	handleAddTransportation = () => {


		const dto = {
            conference : this.props.conference,
			companyName: this.state.name,
            departure : this.state.departure,
		//	departureAddress : this.state.departureAddress,
			departureDate : this.state.departureDate,
			departureTime : this.state.departureTime,
			price : this.state.price,
		};
		this.handleAdd(dto);
	}



	
	handleNameChange = (e) => {
		this.setState({ name: e.target.value });
	}
	handleDepartureChange = (e) => {
		this.setState({ departure: e.target.value });
	}
	handleDepartureAddressChange=(e)=>{
		this.setState({ departureAddress: e.target.value });

	}
	handleDepartureTimeChange=(e)=>{
		this.setState({ departureTime: e.target.value });

	}
	handleDepartureDateChange=(e)=>{
		this.setState({ departureDate: e.target.value });

	}
	handlePriceChange = (e) => {
		this.setState({ price: e.target.value });
	}

	handleModalClose = ()=>{
		this.setState({openModal: false})
	}
	render() {

		return (
			<Modal
				show={this.props.show}
				size="lg"
				dialogClassName="modal-60w-60h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<div style={{ marginBottom: "2rem" }}>
						<div style={{ marginLeft: "0rem" }}>
						
							<div className="row section-design" style={{ border: "1 solid black", }} hidden={this.state.noPicture}>
								<div className="col-lg-8 mx-auto">



									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Company name:</label>
											<input
												className="form-control"
												id="name"
												type="text"
												onChange={this.handleNameChange}
												value={this.state.name}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Departure town:</label>
											<input
												className="form-control"
												id="departure"
												type="text"
												onChange={this.handleDepartureChange}
												value={this.state.departure}
											/>
										</div>
									</div>
								
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Departure date:</label>
											<input
												className="form-control"
												id="departureDate"
												type="date"
												onChange={this.handleDepartureDateChange}
												value={this.state.departureDate}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Departure time:</label>
											<input
												className="form-control"
												id="departureTime"
												type="time"
												onChange={this.handleDepartureTimeChange}
												value={this.state.departureTime}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Price:</label>
											<input
												className="form-control"
												id="price"
												type="number"
												onChange={this.handlePriceChange}
												value={this.state.price}
											/>
										</div>
									</div>
							</div>
							</div>
							<div className="form-group text-center">

								<div>

									<button style={{ width: "10rem", margin: "1rem", background: "#37FF33" }} onClick={this.handleAddTransportation} className="btn btn-outline-secondary btn-sm">Add<br /> </button>

								</div>
							</div>
						</div>

					</div>
					<ModalDialog
                    show={this.state.openModal}
                    onCloseModal={this.handleModalClose}
                    header="Success"
                    text={this.state.textSuccessfulModal}
                />
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default AddTransportation;
