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
        let webShopId = getCookie("webShopId");

        const paymentDTO = {
            paymentId: paymentId,
            payerId :PayerID,
            webShopId : webShopId
        }
        axios.post("http://localhost:9090/paypal-service/api/paypal/success", paymentDTO)
          .then( res =>
               console.log(res),
          )
          .catch(err => console.log(err));

      }, []);
      function getCookie(cname) {
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