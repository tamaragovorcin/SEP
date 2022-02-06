import React, { Component } from "react";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT } from "../../constants.js";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import ModalDialog from "../../components/ModalDialog";
import { withRouter } from 'react-router-dom';
import { Redirect } from "react-router-dom";
import { Carousel } from 'react-responsive-carousel';
import { YMaps, Map } from "react-yandex-maps";


class Conference extends Component {
	state = {
		conference : {},
        accommodations : [{},{}],
		transportations : [{},{}],
		idCart : "",
		formShowed: false,
		title: "",
		description: "",
		engineer: "",	
		redirect: false,
		redirectUrl: "",
		startDate : "",
		endDate : "",
		showAccommodation : true,
		showTransportation : false,
		city : ""
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
	componentDidMount() {
        const idConf = this.props.match.params.idConf;
		const idCart = this.props.match.params.idCart;
		this.setState({
			id: idConf,
			idCart : idCart
		})
		Axios.get(BASE_URL + "/api/conference/"+idConf)
			.then((res) => {
				this.setState({
                  conference : res.data,
                  accommodations : res.data.accommodations,
				  transportations : res.data.transportations,
				  city : res.data.address.city,
				  startDate : res.data.startDate[2]+"."+res.data.startDate[1]+"."+res.data.startDate[0]+".",
				  endDate : res.data.endDate[2]+"."+res.data.endDate[1]+"."+res.data.endDate[0]+"."
                });
					// alert(this.state.startDate)
			})
			.catch((err) => {
				console.log(err);
			});
		
	}



	handleClickOnShowAccommodation =()=>{
		this.setState({showAccommodation:true})
		this.setState({showTransportation:false})
	}

	handleClickOnShowTransportation =()=>{
		this.setState({showAccommodation:false})
		this.setState({showTransportation:true})
	}
	handleDeleteAcc=(id)=>{
		Axios.get(BASE_URL + "/api/conference/deleteAccommodation/" + id,  {  headers: { Authorization: getAuthHeader() } })
		.then((res) => {
			console.log(res.data)
			alert("success")
			window.location.reload();

		})
		.catch((err) => {
			console.log(err);
		});
	}
	
	handleDeleteTr = (id)=>{
		Axios.get(BASE_URL + "/api/conference/deleteTransportation/" + id,  {  headers: { Authorization: getAuthHeader() } })
		.then((res) => {
			console.log(res.data)
			alert("success")
			window.location.reload();

		})
		.catch((err) => {
			console.log(err);
		});
	}
	handleClickOnReserveAcc = (id)=>{
		let dto = {
			cartId: this.state.idCart,
			accommodationId: id,

		};
		Axios.post(BASE_URL_AGENT + "/api/conference/addAccommodationToCart", dto, {  headers: { Authorization: getAuthHeader() } })
				.then((res) => {
					if (res.status === 500) {
						this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
					} else {
						this.setState({openModal: true})
						this.setState({
							hiddenSuccessAlert: false,
							successHeader: "Success",
							successMessage: "You successfully sent a reservation.",
							hiddenEditInfo: true,
						});
						alert("You have successfully reserved accommodation.")
						this.setState({showTransportation : true,
						showAccommodation:false})

						
					}
				})
				.catch((err) => {
					console.log(err);
				});

	}
	handleClickOnReserveTr = (id)=>{
		let dto = {
			cartId: this.state.idCart,
			transportationId: id,

		};
		Axios.post(BASE_URL_AGENT + "/api/conference/addTransportationToCart", dto, {  headers: { Authorization: getAuthHeader() } })
				.then((res) => {
					if (res.status === 500) {
						this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
					} else {
						this.setState({openModal: true})
						/*this.setState({
							hiddenSuccessAlert: false,
							successHeader: "Success",
							successMessage: "You successfully sent a reservation.",
							hiddenEditInfo: true,
						});*/
						alert("You have successfully reserved transportation.")

					}

				})
				.catch((err) => {
					console.log(err);
				});
	}



	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />
                <h3 style={{fontSize:"30px", fontFamily:"fantasy", marginTop:"10%", marginRight:"10%"}} className=" text-center mb-0 mt-10 text-uppercase">{this.state.conference.name}</h3>

                <div id="hero">
                    <div className="container">

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
                        <tr>
                        <td width="100%" style={{display: "block",
  marginLeft:"auto" ,
  marginRight: "auto",
  width: "40%"}}>
										<img className="img-fluid" src={this.state.conference.pictures?.[0]} />
									</td>
                        </tr>

                <tr 
									id={this.state.conference.id}
									key={this.state.conference.id}
									style={{ cursor: "pointer" }}

								>
									
									<td>
										
										<div>
											<b><i>{this.state.conference.slogan}</i> </b> 
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Location: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{this.state.city}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Start date: </b> 
                                           <span style={{fontWeight:"bold",fontSize:"17px"}}>{this.state.startDate}</span>
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>End date: </b> 
                                           <span style={{fontWeight:"bold",fontSize:"17px"}}>{this.state.endDate}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Content: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{this.state.conference.content}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Capacity: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{this.state.conference.capacity}</span>  
										</div>
										


                                    </td>
                                    <td>
                                    <div>
											<b style={{color:"#1977cc", fontSize:"25px"}}>Registration fee: </b> 
                                            <div style={{fontWeight:"bold", fontFamily:"sans-serif",fontSize:"40px"}}>
{this.state.conference.registrationFee}&nbsp; €</div>  
										</div>
                                    </td>
                                   
								</tr>
                                </table>
                                <tr>
									<td> <button
											style={{
												width: "100%",
												marginRight :"10%"
											}}
											onClick={() => this.handleClickOnShowAccommodation()}
											className="btn btn-outline-info btn-xl"
											id="sendMessageButton"
											type="button"
										>
											View accommodation options
									</button></td>
									<td>
									<button
											style={{
												width: "100%",
											}}
											onClick={() => this.handleClickOnShowTransportation()}
											className="btn btn-outline-info btn-xl"
											id="sendMessageButton"
											type="button"
										>
											View transportation options
									</button>
									</td>
								
									
								</tr>


                                {/* **********************ACCOMMODATIONS***************************** */}
								
                                <table hidden={!this.state.showAccommodation} className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
								<h1 style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"35px"}}>
											ACCOMMODATION OPTIONS:
											<br/>
											<br/>
										</h1>
						<tbody>
							{this.state.accommodations.map((accommodation) => (
								
								<tr 
									id={accommodation.id}
									key={accommodation.id}
									style={{ cursor: "pointer" }}
                                    // onClick={() => this.handleClickOnConference(conference.id)}

								>
									<tr>
										<div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {accommodation.name}
										</div>
									</tr>
									<tr> <Carousel>
								{accommodation.pictures?.map(img => (<div style={{height:"500px"}}>
									<img src={img}/>
								</div>))}

							</Carousel></tr>
								<tr>
										<td>
											<b><i>{accommodation.description}</i> </b> 
                                        <div>
											<b style={{color:"#1977cc"}}>Location: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{accommodation.location}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Max capacity: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{accommodation.maxCapacity}</span>  
										</div>
										</td>
										<td>
										<div>
											<b style={{color:"#1977cc"}}>Price: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{accommodation.price}&nbsp; €</span>  
										</div>
										</td>
                                    <td hidden={!this.hasRole("ROLE_ADMIN")}>
                                    <div>  <button
											style={{
												width: "100%",
											}}
											onClick={(e)=>this.handleDeleteAcc(accommodation.id)}
											className="btn btn-primary btn-md"
											id="sendMessageButton"
											type="button"
										>
											Delete
									</button></div>
                                    </td>
                                    <td hidden={!this.hasRole("ROLE_USER")}>
										<div>  <button
											style={{
												width: "100%",

											}}
											onClick={() => this.handleClickOnReserveAcc(accommodation.id)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Reserve
									</button></div>

									</td>
									
									</tr>
									<hr/>
									<hr style={{fontWeight:"bold"}}/>
								</tr>
							))}
						</tbody>
					</table>
                     {/* **********************Transportations***************************** */}
                     <table hidden={!this.state.showTransportation} className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
					 <h1 style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"35px"}}>
											TRANSPORTATION OPTIONS:
											<br/>
											<br/>
										</h1>
						<tbody>
							{this.state.transportations.map((transportation) => (
								<tr 
									id={transportation.id}
									key={transportation.id}
									style={{ cursor: "pointer" ,width: "100%"}}

                                    // onClick={() => this.handleClickOnConference(conference.id)}

								>
									<tr>
									<div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px"}}>
											 {transportation.companyName}
									</div>
									</tr>

									<tr>
									<td>
                                        <div>
											<b style={{color:"#1977cc"}}>Departure town: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{transportation.departure}</span>  
										</div>
                                       
										<div>
											<b style={{color:"#1977cc"}}>Departure date and time: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{transportation.departureDate},&nbsp; {transportation.departureTime}</span>  
										</div>
										
									</td>
										<td>
										<div>
											<b style={{color:"#1977cc"}}>Price: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{transportation.price}&nbsp; €</span>  
										</div>
										</td>
									<td hidden={!this.hasRole("ROLE_ADMIN")}>
                                    <div>  <button
											style={{
												width: "100%",
											}}
											onClick={(e)=>this.handleDeleteTr(transportation.id)}
											className="btn btn-primary btn-md"
											id="sendMessageButton"
											type="button"
										>
											Delete
									</button></div>
                                    </td>
                                    <td hidden={!this.hasRole("ROLE_USER")}>
										<div>  <button
											style={{
												width: "100%",

											}}
											onClick={() => this.handleClickOnReserveTr(transportation.id)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Reserve
									</button></div>

									</td>
									</tr>
										
									
								</tr>
							))}
						</tbody>
					</table>
                                </div>
                                </div>
				
			</React.Fragment>
		);
	}
}

export default withRouter(Conference);