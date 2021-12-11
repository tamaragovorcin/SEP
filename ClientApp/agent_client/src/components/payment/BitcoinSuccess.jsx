import React, { useEffect } from "react";
import { BASE_URL_AGENT, BASE_URL_BITCOIN } from "../../constants.js";
import getAuthHeader from "../../GetHeader";
import Axios from "axios";
import { Link } from 'react-router-dom';
const BitcoinSuccess = () => {

    useEffect(() => {
         const purchaseDTO = {
            address : JSON.parse(localStorage.getItem('orderAddress')),
            products : JSON.parse(localStorage.getItem('orderProducts')),
        }
        var webshopType = JSON.parse(localStorage.getItem('webshopType'))
        if(webshopType==="product") {
            saveProduct(purchaseDTO)
        }else if(webshopType==="conference"){
            saveConference(purchaseDTO.products)
        }
       
    }, []);
    const saveConference = (products)=> {
        const purchaseDTO = {
            items : products
        }
        Axios.post(BASE_URL_AGENT + "/api/conference/addOrder", purchaseDTO, {  headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						localStorage.removeItem('orderAddress')
                        localStorage.removeItem('orderProducts')

                        const bitcoinDto = {
                            total : JSON.parse(localStorage.getItem('totalAmount')),
                            currency : JSON.parse(localStorage.getItem('currency')),
                        }     
                        Axios.post(BASE_URL_BITCOIN + "/api/bitcoin/save", bitcoinDto, {  headers: { Authorization: getAuthHeader() } })
                            .then((res) => {

                            })
                            .catch((err) => {
                                console.log(err);
                            });
                        this.setState({openModal : true})
					})
					.catch((err) => {
						console.log(err);
					});
    }

    const saveProduct = (purchaseDTO)=> {
        Axios.post(BASE_URL_AGENT + "/api/purchase/add", purchaseDTO, {  headers: { Authorization: getAuthHeader() } })
        .then((res) => {

            localStorage.removeItem('orderAddress')
            localStorage.removeItem('orderProducts')

            const bitcoinDto = {
                total : JSON.parse(localStorage.getItem('totalAmount')),
                currency : JSON.parse(localStorage.getItem('currency')),
            }     
            Axios.post(BASE_URL_BITCOIN + "/api/bitcoin/save", bitcoinDto, {  headers: { Authorization: getAuthHeader() } })
                .then((res) => {

                })
                .catch((err) => {
                    console.log(err);
                });
            this.setState({openModal : true})
        })
        .catch((err) => {
            console.log(err);
        });
    }
    return (
        
        <React.Fragment>
            <div>
            
                <h2 >
                    Successful payment with bitcoin!
                </h2>
                <Link to="/">Back to web shop</Link>
            </div>
        </React.Fragment>
    );   
}

export default BitcoinSuccess;