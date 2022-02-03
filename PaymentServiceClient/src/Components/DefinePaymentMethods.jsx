import React, { Component } from "react";
import Axios from "axios";

import qr from '../Static/img/qr.png';
import card from '../Static/img/bank_cards.jpg';
import bitcoin from '../Static/img/bitcoin.png';
import paypal from '../Static/img/paypall.png';
import '../App.css';
import MerchantModal from "./MerchantModal";

class DefinePaymentMethods extends Component {
	state = {
        enabledMethods: [],
        disabledMethods: [],
        card : false,
        paypal : false,
        bitcoin: false,
        qr : false,
        merchantFormShow : false,
        banks : [],
        chosenMethod : ""
	};


	componentDidMount() {
       this.handleGetMethods();
       this.handleGetBanks();
	}

    handleGetBanks(){
        Axios.get( "http://localhost:8088/api/bank/all")
        .then((res) => {
            console.log(res.data)
            this.setState({ banks: res.data});
        })
        .catch((err) => {
            console.log(err);
        });
    }
    handleGetMethods(){
        let webshopId = JSON.parse(localStorage.getItem("webShopId"));
        Axios.get( "http://localhost:9090/auth-service/api/webshop/allMethods/"+webshopId)
			.then((res) => {
                console.log(res.data)
                this.setState({ enabledMethods: res.data.methods});
                this.setState({ disabledMethods: res.data.disabledMethods});
			})
			.catch((err) => {
				console.log(err);
			});
    }
    handleDisableMethod(method) {
        let webshopId = JSON.parse(localStorage.getItem("webShopId"));
        let dto = { webShopId: webshopId, methodName: method };

        Axios.post( "http://localhost:9090/auth-service/api/webshop/disablePaymentMethod/", dto)
			.then((res) => {alert("Successfully disabled payment method " + method);
                 this.handleGetMethods();
			})
			.catch((err) => {
               alert("Error with disabling payment method " + method); 
			   console.log(err);
            });
    } 
    handleEnableMethod(method) {
        if(method == "card" || method == "qr"){
            this.setState({merchantFormShow : true})            
            this.setState({chosenMethod : method})
        }else{
        let webshopId = JSON.parse(localStorage.getItem("webShopId"));
        let dto = { webShopId: webshopId, methodName: method };

        Axios.post( "http://localhost:9090/auth-service/api/webshop/enablePaymentMethod/", dto)
			.then((res) => {alert("Successfully enabled payment method " + method);
             this.handleGetMethods();
			})
			.catch((err) => {
               alert("Error with enabling payment method " + method); 
			   console.log(err);
            });
        }
    } 
    handleCloseMerchantModal = ()=>{
        this.setState({merchantFormShow : false})
    }
 
	render() {
		return (
			<React.Fragment>
				<div className="container" style={{ marginTop: "2%" }}>
					
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Choose payment method that this webshop accepts.
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto text-center" >
                         <table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
                            <tbody>

                                {this.state.enabledMethods.map((method) => (
                                    <tr 
                                        id={method}
                                        key={method}

                                    >
                                        <td width="130em">
                                            {method==="qr" && <img src={qr} className="App-logo-small" />}
                                            {method==="paypal" && <img src={paypal} className="App-logo-small" />}
                                            {method==="bitcoin" && <img src={bitcoin} className="App-logo-small" />}
                                            {method==="card" && <img src={card} className="App-logo-small" />}

                                            <label>{method}</label>
                                        </td>
                                        <td>
                                            <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px", style:"underline"}}>
                                                <button onClick={() => this.handleDisableMethod(method) }>DISABLE</button>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                               
                            
						     </tbody>
				    	    </table>
						</div>
                        <div className="col-lg-8 mx-auto text-center" >
                         <table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
                            <tbody>

                                {this.state.disabledMethods.map((method) => (
                                    <tr 
                                        id={method}
                                        key={method}

                                    >
                                        <td width="130em">
                                            {method==="qr" && <img src={qr} className="App-logo-small" />}
                                            {method==="paypal" && <img src={paypal} className="App-logo-small" />}
                                            {method==="bitcoin" && <img src={bitcoin} className="App-logo-small" />}
                                            {method==="card" && <img src={card} className="App-logo-small" />}
                                            <label>{method}</label>
                                        </td>
                                        <td>
                                            <div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px", style:"underline"}}>
                                                <button onClick={() => this.handleEnableMethod(method) }>ENABLE</button>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                               
                            
						     </tbody>
				    	    </table>
						</div>
                      
					</div>
                    <MerchantModal
					buttonName="Add"
					header="Define merchant information"
					show={this.state.merchantFormShow}
					onCloseModal={this.handleCloseMerchantModal}
                    banks = {this.state.banks}
                    method = {this.state.chosenMethod}
                    handleSave = {this.handleSave}
				/>
				</div>
			
			</React.Fragment>
		);
	}
}

export default DefinePaymentMethods;