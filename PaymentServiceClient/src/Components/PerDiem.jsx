import React, { Component } from "react";
import { Link } from "react-router-dom";
import Axios from "axios";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";


class PerDiem extends Component {
	state = {
		banks: [],
		redirect: false,
		redirectUrl : "",
        giroNumber : "",
        cardHolderName : "",
        referenceNumber : "",
        amount : 0,
        showForm : true,
        showMerchant : false,
        merchantId : "",
        merchantPassword : "",
        paymentId: "",
        paymentUrl: "",
		currency : "dinar",



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
	

    getCookie = (cname) => {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(let i = 0; i <ca.length; i++) {
          let c = ca[i];
          while (c.charAt(0) == ' ') {
            c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
          }
        }
        return "";
    }


	componentDidMount() {
	
	}
    handleGiroNumberChange = (event) => {
		this.setState({ giroNumber: event.target.value });
	};
    handleCardHolderNameChange = (event) => {
		this.setState({ cardHolderName: event.target.value });
	};
    handleCardReferenceNumberChange = (event) => {
		this.setState({ referenceNumber: event.target.value });
	};
    handleAmountChange = (event) => {
		this.setState({ amount: event.target.value });
	};
	handleCurrencyChange = (e)=>{
		this.setState({ currency: e.target.value });

	}

    handleRegister = ()=>{
        let dto = {
            cardHolderName: this.state.cardHolderName,
            giroNumber: this.state.giroNumber,
            referenceNumber : this.state.referenceNumber,
            amount : this.state.amount,
			currency : this.state.currency
        };
        
                Axios.post('http://localhost:8088/payment/payThePerDiem', dto, { validateStatus: () => true })
                    .then((res) => {
                        if (res.status === 409) {
                            this.setState({
                                errorHeader: "Resource conflict!",
                                hiddenErrorAlert: false,
                            });
                        } else if (res.status === 500) {
                            this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
                        } else if (res.status === 400) {
							alert("Data you entered is not valid, please try again.")
                        } 
						else {
                            console.log("Success");
                            console.log(res.data)
                            this.setState({showForm:false})
							alert("You have successfully paid the per diem for the employee")
							window.location.href="http://localhost:3002/#"
                        }
                    })
                    .catch((err) => {
                        console.log(err);
						alert("You have successfully paid the per diem for the employee")

                    });
            
        
    }
   
	render() {
        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
            <React.Fragment>

<div id="center">
        <div className="container d-flex align-items-center" style={{ marginTop: "10%" , width : "40%"}}>
					<div className="row section-design">
						<div>
							<br />
                            <h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
					Pay the per diem to the employee
                    (Enter recipient  name, account number, reference number and amount of money)
					</h5>
							<form id="contactForm" name="sentMessage">
							<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Recipien:*</label>
										<input
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleCardHolderNameChange}
											value={this.state.cardHolderName}
										/>
									</div>
								
								</div>
                            <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Account number:*</label>
										<input
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleGiroNumberChange}
											value={this.state.giroNumber}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Reference number:*</label>
										<input
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleCardReferenceNumberChange}
											value={this.state.referenceNumber}
										/>
									</div>
								
								</div>
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Ammount of money:*</label>
										<input
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handleAmountChange}
											value={this.state.amount}
										/>
									</div>
								
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Currency:*</label>
										<select className='form-control'
										value={this.state.currency}
										onChange={this.handleCurrencyChange}>
										<option value="dolar">$</option>
										<option value="dinar">DIN</option>
										<option value="euro">€</option>
									
										</select>
									</div>
								
								</div>
								
								
								<div className="form-group">
									<button
									
										onClick={this.handleRegister}
										className="btn btn-primary
                                         btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Confirm
									</button>
								</div>
							</form>



                          


						</div>
                        </div>

        </div>
      

        
    </div>
            </React.Fragment>

		);
	}
}

export default PerDiem;
