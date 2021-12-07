import React, { useEffect } from "react";
import { BASE_URL_AGENT } from "../../constants.js";
import getAuthHeader from "../../GetHeader";
import Axios from "axios";
import { Link } from 'react-router-dom';
const PayPalSuccess = () => {

    useEffect(() => {
         const purchaseDTO = {
            address : JSON.parse(localStorage.getItem('orderAddress')),
            products : JSON.parse(localStorage.getItem('orderProducts')),
        }
        console.log(purchaseDTO.address)
        console.log(purchaseDTO.products)

        Axios.post(BASE_URL_AGENT + "/api/purchase/add", purchaseDTO, {  headers: { Authorization: getAuthHeader() } })
							.then((res) => {
							
                                localStorage.removeItem('orderAddress')
                                localStorage.removeItem('orderProducts')
                                this.setState({openModal : true})
							})
							.catch((err) => {
								console.log(err);
							});
    }, []);

    return (
        
        <React.Fragment>
            <div>
            
                <h2 >
                    Successful payment with paypal!
                </h2>
                <Link to="/">Back to web shop</Link>
            </div>
        </React.Fragment>
    );   
}

export default PayPalSuccess;