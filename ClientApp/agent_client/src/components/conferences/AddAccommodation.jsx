import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import ImageUploader from 'react-images-upload';
import Axios from "axios";
import { BASE_URL_AGENT } from "../../constants.js";
import ModalDialog from "../ModalDialog";
import { YMaps, Map } from "react-yandex-maps";
import getAuthHeader from "../../GetHeader";
const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};
class AddAccommodation extends Component {
	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
		this.state = {
			name: "",
            descriptiption : "",
			address: "",
			addressError: "none",
			addressNotFoundError: "none",
            maxCapacity : 0,
            price: 0,
			pictures: [],
            facilities : [],
			openModal: false,
			help: [],
			fileUploadOngoing: false
		}
		this.onDrop = this.onDrop.bind(this);

	}
	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};
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
		Axios.post(BASE_URL_AGENT + "/api/conference/addAccommodation" , dto, {  headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				let confId = res.data;
				this.state.pictures.forEach((pic) => {
					this.test(pic, confId);
				});
				this.setState({ textSuccessfulModal: "You have successfully added accommodation for conference." });
				this.props.onCloseModal()

			})
			.catch((err) => {
				console.log(err);
			});
		this.setState({ openModal: true });
	}
	handleAddAccommodation = () => {
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
					conference : this.props.conference,
					name: this.state.name,
					description : this.state.descriptiption,
					address : foundAddress,
					maxCapacity : this.state.maxCapacity,
					price : this.state.price,
					pictures: h,
					facilities : this.state.facilities
				};
					this.handleAdd(dto);
	});


	
		
	}



	
	handleNameChange = (e) => {
		this.setState({ name: e.target.value });
	}
	handleLocationChange = (e) => {
		this.setState({ address: e.target.value });
	}
	handleDescriptionChange = (e) => {
		this.setState({ descriptiption: e.target.value });
	}
	handleCapacityChange = (e) => {
		this.setState({ maxCapacity: e.target.value });
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
											<label>Accommodation name:</label>
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
											<label>Description:</label>
											<input
												className="form-control"
												id="description"
												type="text"
												onChange={this.handleDescriptionChange}
												value={this.state.descriptiption}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Address:</label>
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
											<label>Max capacity:</label>
											<input
												className="form-control"
												id="capacity"
												type="number"
												onChange={this.handleCapacityChange}
												value={this.state.maxCapacity}
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

									<button style={{ width: "10rem", margin: "1rem", background: "#37FF33" }} onClick={this.handleAddAccommodation} className="btn btn-outline-secondary btn-sm">Add<br /> </button>

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

export default AddAccommodation;
