import React from "react";
import { useEffect } from "react";
import { Link } from 'react-router-dom';
import Axios from "axios";

const ConferencePaymentFailure = () => {


    useEffect(() => {
        let paymentId = getCookie("paymentWebShopId");
          const dto = {
            paymentId : paymentId,
            status : "CANCELED",
          }
          Axios.post("http://localhost:8085/api/conference/update/",dto)
          .then((res) => {
            console.log(res)
          })
          .catch((err) => {
            console.log(err);
          })
    });

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
                    Failure with payment!
                </h2>
                <Link to="/">Back to web shop</Link>
            </div>
        </React.Fragment>
    );   
}

export default ConferencePaymentFailure;