import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Axios from "axios";
const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};
class MerchantModal extends Component {

		state = {
			merchantID : "",
            merchantPassword : "",
            merchantValid : false,
            bank : "1",
			openModal: false,
			help: [],
			fileUploadOngoing: false
		}

	
	



	
    handleAdd=()=>
{
        let merchant ={merchantID : this.state.merchantID, merchantPassword : this.state.merchantPassword, bank : this.state.bank}
        Axios.post( "http://localhost:8088/api/bank/merchantSearch",merchant)
			.then((res) => {
                console.log(res.data)
                this.setState({ merchantValid: res.data.valid});
                alert(res.data.valid)

                if(res.data.valid){
                    let webshopId = JSON.parse(localStorage.getItem("webShopId"));
                    let dto = { webShopId: webshopId, methodName: this.props.method ,merchantID : this.state.merchantID, merchantPassword : this.state.merchantPassword, bank : this.state.bank };
            
                    Axios.post( "http://localhost:9090/auth-service/api/webshop/enableBankPaymentMethod/", dto)
                        .then((res) => {alert("Successfully enabled payment method " + this.props.method);
                        this.props.onCloseModal();
                         this.handleGetMethods();
                        })
                        .catch((err) => {
                           alert("Error with enabling payment method " +this.props.method); 
                           console.log(err);
                        });
                    }
                    else{
                        alert("Enetered data is not valid, please try again")
                    }
			})
			.catch((err) => {
				console.log(err);
			});
        
}



	
	handleIDChange = (e) => {
		this.setState({ merchantID: e.target.value });
	}
	handlePasswordChange = (e) => {
		this.setState({ merchantPassword: e.target.value });
	}
	handleBankChange = (e) => {
		this.setState({ bank: e.target.value });
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
											<label>Merchant ID:*</label>
											<input
												className="form-control"
												id="name"
												type="text"
												onChange={this.handleIDChange}
												value={this.state.merchantID}
											/>
										</div>
									</div>
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Merchant Password:*</label>
											<input
												className="form-control"
												id="description"
												type="text"
												onChange={this.handlePasswordChange}
												value={this.state.merchantPassword}
											/>
										</div>
									</div>
								
									<div className="control-group">
										<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
									<label>Bank:</label>
									<select onChange = {this.handleBankChange} className="selectpicker btn-primary-outline form-control" style={{ }}>
                                                                {this.props.banks.map((bank) => (
                                                                    <option id={bank.id} key={bank.id} value ={bank.id}>
                                                                    {bank.name}
                                                                    </option>

                                                                 ))}
                                                            </select>
								</div>
											
									</div>
							</div>
							</div>

                            <div  className="form-group text-center">
                                            <Button className="mt-3 btn btn-primary btn-lg"  onClick = {() => this.handleAdd()} style ={{width:"80px"}}>Save</Button>
                                        </div>
							</div>

					</div>
					
				</Modal.Body>
				
			</Modal>
		);
	}

}
export default MerchantModal;
