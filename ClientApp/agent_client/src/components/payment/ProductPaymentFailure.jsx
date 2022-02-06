import React from "react";
import { useEffect } from "react";
import { Link } from 'react-router-dom';
import Axios from "axios";

const ProductPaymentFailure = () => {
    useEffect(() => {
        let paymentId = this.getCookie("paymentWebShopId");
		const dto = {
			paymentId : paymentId,
			status : "CANCELED",
		}
		Axios.post("http://localhost:8085/api/purchase/update/",dto)
			.then((res) => {
				console.log(res.data)
			.catch((err) => {
				console.log(err);
			});

        });
    })
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

export default ProductPaymentFailure;