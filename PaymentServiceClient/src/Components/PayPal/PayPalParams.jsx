import React from "react";
import axios from "axios";
import { useEffect } from 'react';

const PayPalParams = () => {

    useEffect(() => {
       
        const queryString = window.location.href
        const myArray = queryString.split("?");
        const params = myArray[1].split("&")
        const paymentId = params[0].split("=")[1]
        const PayerID = params[2].split("=")[1]
        const paymentDTO = {
            paymentId: paymentId,
            payerId :PayerID
        }
        axios.post("http://localhost:9090/paypal-service/api/paypal/success", paymentDTO)
          .then( res =>
               console.log(res),
          )
          .catch(err => console.log(err));

      }, []);

    return (
        <React.Fragment>
            <div>
            
                <h2 >
                    Processing payment...
                </h2>

            </div>
        </React.Fragment>
    );   
}

export default PayPalParams;