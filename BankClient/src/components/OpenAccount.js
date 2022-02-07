import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import SuccessfulOpeningAccount from "./SucceessfulOpeningAccount.js";


class OpenAccount extends Component {
	state = {
		banks: [],
		redirect: false,
		redirectUrl : "",
		name: "",
		ucin : "",
		idCardNumber : "",
		phoneNumber : "",
		id : "",
		showSuccessModal : false,
		pan : "",
		cardSecurityCode : "",
		expirationDate : ""
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

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};
    handleUCINChange = (event) => {
		this.setState({ ucin: event.target.value });
	};
	handleIdCardNumberChange = (event) => {
		this.setState({ idCardNumber: event.target.value });
	};
	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};
	handleSuccessModalClose = ()=>{
		this.setState({ showSuccessModal:false });

	}
	handleSignUp= () => {
		let dto = {
            cardHolderName: this.state.name,
            // cardHolderIdCardNumber: this.state.idCardNumber,
            cardHolderUCIN: this.state.ucin,
            // cardHolderPhoneNumber: this.state.phoneNumber,
			bankId :this.props.bank.id

        };
        
                Axios.post(BASE_URL + "/api/bank/openAnAccount", dto, { validateStatus: () => true })
                    .then((res) => {
                        if (res.status === 409) {
                            this.setState({
                                errorHeader: "Resource conflict!",
                                hiddenErrorAlert: false,
                            });
                        } else if (res.status === 500) {
                            this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
                        } else {
                            console.log("Success");
                            this.setState({pan : res.data.pan})
                            this.setState({cardSecurityCode : res.data.cardSecurityCode})
                            this.setState({ expirationDate: res.data.expirationDate[1]+"/" + res.data.expirationDate[0] });
							this.setState({cardHolderUCIN : res.data.cardHolderUCIN})
                            this.setState({cardHolderName : res.data.cardHolderName})
                            this.setState({showSuccessModal:true})

                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            
	};
  

	render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
            <React.Fragment>


<div>
        <div className="container d-flex align-items-center" style={{ marginTop: "10%" }}>
					<div className="row section-design"  style={{  margin: "auto",
							width: "50%",
							border: "2px solid blue",
							padding: "10px",
							textAlign: "center",
							backgroundColor:"#E8EDEE"
							}}>
						<div style={{display: "block",
									marginLeft: "auto",
									marginRight: "auto",
									width: "70%"}}>
							<br />
							<form id="contactForm" name="sentMessage" noValidate="novalidate">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Your name:</label>
										<input
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label> UCIN(Your unique citizens number):</label>
										<input
											class="form-control"
											type="text"
											id="ucin"
											onChange={this.handleUCINChange}
											value={this.state.ucin}
										/>
									</div>
									
								</div>
							
                                
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Phone number:</label>
										<input
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<label>Identity card number:</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											class="form-control"
											type="text"
											onChange={this.handleIdCardNumberChange}
											value={this.state.idCardNumber}
										/>
									</div>
								
								</div>
								
								<div className="form-group">
									<button
									
										onClick={this.handleSignUp}
										className="btn btn-info btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Open an account
									</button>
								</div>
							</form>
						</div>
                        </div>

        </div>
      
<SuccessfulOpeningAccount
						show = {this.state.showSuccessModal}
						onCloseModal = {this.handleSuccessModalClose}
	                    pan={this.state.pan}
						expirationDate = {this.state.expirationDate}
						cardSecurityCode = {this.state.cardSecurityCode}
						cardHolderName = {this.state.cardHolderName}
						cardHolderUCIN = {this.state.cardHolderUCIN}

/>
        
    </div>
            </React.Fragment>

		);
	}
}

export default OpenAccount;
