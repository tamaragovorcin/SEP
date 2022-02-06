import React, { Component } from "react";
import TopBar from "../TopBar";
import Header from "../Header";
import Axios from "axios";

class ProductPaymentSuccess extends Component {
	state = {
		products: {
			status : "",
			address : {
				country : "",
				street : "",
				city : "",
			},
			products : []
		},
		productsList : []
	};

	componentDidMount() {
        let paymentId = this.getCookie("paymentWebShopId");
		const dto = {
			paymentId : paymentId,
			status : "FINISHED",
		}
		Axios.post("http://localhost:8085/api/purchase/update/",dto)
			.then((res) => {
				Axios.get("http://localhost:8085/api/purchase/"+paymentId)
					.then((res) => {
						this.setState({ products: res.data });
						this.setState({ productsList: res.data.products });

						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
					})
			.catch((err) => {
				console.log(err);
			});

	}
     getCookie(cname) {
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

	render() {

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Successful payment!</h5>


					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
								<div>
									<p><label>  Status: {this.state.products.status} </label></p>
									<p><label>    Deliver to address: {this.state.products.address.country}, {this.state.products.address.street}, {this.state.products.address.city} </label></p>
									<p><label >   Date of reservation: {this.state.products.dateOfReservation} </label></p>
									
									{this.state.productsList.map((item) => (
								<tr
									id={this.state.products.id}
									key={this.state.products.id}
									style={{ cursor: "pointer" }}

								>
								
									<td width="130em">
									<img className="img-fluid" src={item.pictures?.[0]} width="70em" />
									</td>
									<td>
										<div>
											<b>Name: </b> {item.name}
										</div>
										<div>
											<b>Price: </b> {item.price}
										</div>
										<div>
											<b>Quantity: </b> {item.quantity}
										</div>
									</td>
								</tr>
									))}
							
									</div>
						</tbody>
					</table>
				</div>
			
			</React.Fragment>
		);
	}
}

export default ProductPaymentSuccess;

