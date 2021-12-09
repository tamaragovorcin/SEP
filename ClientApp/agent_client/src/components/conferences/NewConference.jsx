import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import ImageUploader from 'react-images-upload';
import Axios from "axios";
import { BASE_URL_AGENT } from "../../constants.js";
import ModalDialog from "../ModalDialog";
import getAuthHeader from "../../GetHeader";
import { YMaps, Map } from "react-yandex-maps";

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};
class NewConference extends Component {
	
	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
		this.state = {
			name: "",
			slogan: "",
            address: "",
            startDate: "",
            endDate: "",
            content: "",
            capacity : 0,
			registrationFee : 0,
			pictures: [],
			openModal: false,
			help: [],
			fileUploadOngoing: false,
			address: "",
		addressError: "none",
		addressNotFoundError: "none",
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
	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};
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
        fetch(BASE_URL_AGENT + "/api/conference/uploadImage", options);
    }
	handleAdd(dto) {
		
		Axios.post(BASE_URL_AGENT + "/api/conference/add" , dto, {  headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				let confId = res.data;
				this.state.pictures.forEach((pic) => {
					this.test(pic, confId);
				});
				this.setState({ textSuccessfulModal: "You have successfully added conference." });
				this.setState({ openModal: true });
				this.props.onCloseModal();
				window.location.reload();




			})
			.catch((err) => {
				console.log(err);
			});
	}
	handleAddConference = () => {
	
		let h = []
		this.state.pictures.forEach((pic) => {
			h.push(pic.name)
			this.setState({
				help: this.state.help.concat(pic.name),
			});
		});

		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let found = true;
		this.ymaps
			.geocode(this.addressInput.current.value, {
				results: 1,
			})
			.then(function (res) {
				if (typeof res.geoObjects.get(0) === "undefined") found = false;
				else {
					var firstGeoObject = res.geoObjects.get(0),
						coords = firstGeoObject.geometry.getCoordinates();
					latitude = coords[0];
					longitude = coords[1];
					country = firstGeoObject.getCountry();
					street = firstGeoObject.getThoroughfare();
					city = firstGeoObject.getLocalities().join(", ");
				}
			})
			.then((res) => {
				var foundAddress= { street, country, city, latitude, longitude }
                    
					const dto = {
						name: this.state.name,
						slogan : this.state.slogan,
						address : foundAddress,
						startDate : this.state.startDate,
						endDate : this.state.endDate,
						content : this.state.content,
						capacity : this.state.capacity,
						registrationFee : this.state.registrationFee,
						pictures: h
					};
					this.handleAdd(dto);
	});
}



	
	handleNameChange = (e) => {
		this.setState({ name: e.target.value });
	}
	handleSloganChange = (e) => {
		this.setState({ slogan: e.target.value });
	}
	handleLocationChange = (e) => {
		this.setState({ address: e.target.value });
	}
	handleStartDateChange = (e) => {
		this.setState({ startDate: e.target.value });
	}
	handleEndDateChange = (e) => {
		this.setState({ endDate: e.target.value });
	}
	handleContentChange = (e) => {
		this.setState({ content: e.target.value });
	}
	handleCapacityChange = (e) => {
		this.setState({ capacity: e.target.value });
	}
	handleRegistraionFeeChange = (e) => {
		this.setState({ registrationFee: e.target.value });
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
							<ImageUploader
								withIcon={false}
								buttonText='Add new photo/video'
								onChange={this.onDrop}
								imgExtension={['.jpg', '.gif', '.png', '.gif']}
								withPreview={true}
							/>
							<div className="row section-design" style={{ border: "1 solid black", }} hidden={this.state.noPicture}>
								<div className="col-lg-8 mx-auto">



									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Conference name:</label>
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
											<label>Slogan:</label>
											<input
												className="form-control"
												id="slogan"
												type="text"
												onChange={this.handleSloganChange}
												value={this.state.slogan}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Location:</label>
											<input className="form-control" id="suggest" ref={this.addressInput} placeholder="Address" />
											<YMaps
										query={{
											load: "package.full",
											apikey: "b0ea2fa3-aba0-4e44-a38e-4e890158ece2",
											lang: "en_RU",
										}}
									>
										<Map
											style={{ display: "none" }}
											state={mapState}
											onLoad={this.onYmapsLoad}
											instanceRef={(map) => (this.map = map)}
											modules={["coordSystem.geo", "geocode", "util.bounds"]}
										></Map>
									</YMaps>
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.addressNotFoundError }}>
										Sorry. Address not found. Try different one.
									</div>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Start date:</label>
											<input
												className="form-control"
												id="startDate"
												type="date"
												onChange={this.handleStartDateChange}
												value={this.state.startDate}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>End date:</label>
											<input
												className="form-control"
												id="endDate"
												type="date"
												onChange={this.handleEndDateChange}
												value={this.state.endDate}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Content:</label>
											<input
												className="form-control"
												id="content"
												type="text"
												onChange={this.handleContentChange}
												value={this.state.content}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Capacity:</label>
											<input
												className="form-control"
												id="capacity"
												type="number"
												onChange={this.handleCapacityChange}
												value={this.state.capacity}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Registration fee:</label>
											<input
												className="form-control"
												id="registrationFee"
												type="number"
												onChange={this.handleRegistraionFeeChange}
												value={this.state.registrationFee}
											/>
										</div>
									</div>
							</div>
							</div>
							<div className="form-group text-center">

								<div>

									<button style={{ width: "10rem", margin: "1rem", background: "#37FF33" }} onClick={this.handleAddConference} className="btn btn-outline-secondary btn-sm">Add<br /> </button>

								</div>
							</div>
						</div>

					</div>
					<ModalDialog
                    show={this.state.openModal}
                    onCloseModal={this.handleModalClose}
                    header="Success"
                    text="You have successfully added new conference/course."
                />
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default NewConference;
