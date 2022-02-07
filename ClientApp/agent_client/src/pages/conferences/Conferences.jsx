import React from "react";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT } from "../../constants.js";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import ModalDialog from "../../components/ModalDialog";
import ReservationModal from "../../components/conferences/ReservationModal";
import AddAccommodation from "../../components/conferences/AddAccommodation";
import AddTransportation from "../../components/conferences/AddTransportation";
import { Redirect } from "react-router-dom";

class Conferences extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            conferences: [],
            conference : {},
            key: 1 | props.activeKey,
            hiddenEditInfo: true,
            price: "",
            quantity: "",
            name: "",
            id: "",
            user: "",
            product: "",
            showReservationModal: false,
            showAddAccommodation : false,
            showAddTransportation : false,
            pictures: [],
            openModal: false,
            help: [],
            fileUploadOngoing: false,
            albumId: "",
            redirect : false


        }

        this.handleSelect = this.handleSelect.bind(this);
        this.onDrop = this.onDrop.bind(this);
    }
  
	hasRole = (reqRole) => {
        let roles = JSON.parse(localStorage.getItem("keyRole"));

        if (roles === null) return false;

        if (reqRole === "*") return true;

        for (let role of roles) {
            if (role === reqRole) return true;
        }
        return false;
    };

    onDrop(picture) {
        this.setState({
            pictures: [],
        });
        this.setState({
            pictures: this.state.pictures.concat(picture),
        });

    }
   



    AddToCart = (id) => {
        this.setState({ product: id });
        this.setState({ showOrderModal: true });

    };
    handleSelect(key) {
        this.setState({ key })
    }
    handleNameChange = (event, id) => {
        let help = this.state.albums
        help.forEach(post => {
            if (post.Id === id) {
                post.Name = event.target.value
            }
        })

        this.setState({ albums: help });
    };

    handlePriceChange = (event, id) => {
        let help = this.state.albums
        help.forEach(post => {
            if (post.Id === id) {
                post.Price = event.target.value
            }
        })

        this.setState({ albums: help });
    };
    handleOrderModalClose = () => {
        this.setState({ showOrderModal: false });
    };
    handleQuantityChange = (event, id) => {
        let help = this.state.albums
        help.forEach(post => {
            if (post.Id === id) {
                post.Quantity = event.target.value
            }
        })

        this.setState({ albums: help });
    };

    handleDelete = (e,id) => {
        Axios.get(BASE_URL + "/api/conference/delete/" + id,  {  headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                console.log(res.data)
                alert("success")
                window.location.reload();

            })
            .catch((err) => {
                console.log(err);
            });

    }
    handleAddAccommodation =(e,id)=>{
        this.setState({conference : id})
        this.setState({showAddAccommodation : true})
    }
    handleAddTransportation =(e,id)=>{
        this.setState({conference : id})
        this.setState({showAddTransportation : true})
    }
    handleCloseAccommodationModal =()=>{
        this.setState({showAddAccommodation : false})
    }
    handleCloseTransportationModal =()=>{
        this.setState({showAddTransportation : false})
    }

    handleClickOnReserve = (e) => {
		this.setState({ conference: e });
		this.setState({ showReservationModal: true });
	};
    handleClickOnSubscribe = (e)=>{

    }
   
	handleClickOnConference= (id) => {
		this.setState({
			redirect: true,
			redirectUrl: "/conference/" + id+"/n",
		});
	};
    handleReservationModalClose = () => {
		this.setState({ showReservationModal: false });
	};

    componentDidMount() {
		
		Axios.get(BASE_URL + "/api/conference/all")
			.then((res) => {
				this.setState({ conferences: res.data });
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

                <h3 style={{fontSize:"30px", fontFamily:"fantasy", marginTop:"10%"}} className=" text-center mb-0 mt-10 text-uppercase">All conferences</h3>

                <div id="hero">
                    <div className="container">

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.conferences.map((conference) => (
								<tr 
									id={conference.id}
									key={conference.id}
									style={{ cursor: "pointer" }}
                                    //  onClick={() => this.handleClickOnConference(conference.id)}

								>
									<td width="130em">
										<img className="img-fluid" src={conference.pictures?.[0]} />
									</td>
									<td>
										<div style={{fontWeight:"bold", fontFamily:"fantasy",fontSize:"25px", style:"underline"}}>
											 <button onClick={() => this.handleClickOnConference(conference.id) }>{conference.name}</button>
										</div>
										<div>
											<b><i>{conference.slogan}</i> </b> 
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Location: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"25px"}}>{conference.address.city}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Start date: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{conference.startDate[2]}.{conference.startDate[1]}.{conference.startDate[0]}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>End date: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{conference.endDate[2]}.{conference.endDate[1]}.{conference.endDate[0]}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Content: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{conference.content}</span>  
										</div>
                                        <div>
											<b style={{color:"#1977cc"}}>Capacity: </b> 
                                          <span style={{fontWeight:"bold",fontSize:"17px"}}>{conference.capacity}</span>  
										</div>
										


                                    </td>
                                    <td>
                                    <div>
											<b style={{color:"#1977cc", fontSize:"25px"}}>Registration fee: </b> 
                                            <div style={{fontWeight:"bold", fontFamily:"sans-serif",fontSize:"40px"}}>
{conference.registrationFee}&nbsp; €</div>  
										</div>
                                    </td>
                                    <td>
										<div hidden={!this.hasRole("ROLE_USER")}>  <button
											style={{
												background: "#1977cc",
												width: "100%",
											}}
											onClick={() => this.handleClickOnReserve(conference)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Reserve
									</button></div>

									</td>
                                    <td>
										<div hidden={!this.hasRole("ROLE_USER")}>  <button
											style={{
												background: "#1977cc",
												width: "100%",
											}}
											onClick={() => this.handleClickOnSubscribe(conference)}
											className="btn btn-primary btn-xl"
											id="sendMessageButton"
											type="button"
										>
											Subscribe to course/conference
									</button></div>

									</td>
                                    <td>
                                    <div hidden={!this.hasRole("ROLE_ADMIN")}>  <button
											style={{
												background: "#1977cc",
												width: "135px",
                                                height : "60px",
                                                marginTop : "100px"
											}}
											onClick={(e)=>this.handleDelete(e,conference.id)}
											className="btn btn-primary btn-md"
											id="sendMessageButton"
											type="button"
										>
											Delete
									</button></div>
                                    </td>
                                    <td>
                                    <div hidden={!this.hasRole("ROLE_ADMIN")}>  <button
											style={{
												background: "#1977cc",
												width: "135px",
                                                height : "60px",
                                                marginTop : "100px"
											}}
											onClick={(e)=>this.handleAddAccommodation(e,conference.id)}
											className="btn btn-primary btn-md"
											id="sendMessageButton"
											type="button"
										>
											Add accommodation
									</button></div>
                                    

                                    </td>
                                    <td>
                                    <div hidden={!this.hasRole("ROLE_ADMIN")}>  <button
											style={{
												background: "#1977cc",
												width: "135px",
                                                height : "60px",
                                                marginTop : "100px"
											}}
											onClick={(e)=>this.handleAddTransportation(e,conference.id)}
											className="btn btn-primary btn-md"
											id="sendMessageButton"
											type="button"
										>
											Add transportation
									</button></div>

                                    </td>
								</tr>
							))}
						</tbody>
					</table>
                    </div>
				</div>
				<ModalDialog
                    show={this.state.openModal}
                    onCloseModal={this.handleModalClose}
                    header="Success"
                    text="You have successfully removed the conference."
                />
                <ReservationModal
					buttonName="Add"
					header="Reserve conference"
					show={this.state.showReservationModal}
					onCloseModal={this.handleReservationModalClose}
					handleSizeChange={this.handleSizeChange}
					conference={this.state.conference}
				/>
                 <AddAccommodation
					buttonName="Add"
					header="Adding accommodation for conference"
					show={this.state.showAddAccommodation}
					onCloseModal={this.handleCloseAccommodationModal}
					handleSizeChange={this.handleSizeChange}
					conference={this.state.conference}
				/>
                <AddTransportation
					buttonName="Add"
					header="Adding transportation for conference"
					show={this.state.showAddTransportation}
					onCloseModal={this.handleCloseTransportationModal}
					handleSizeChange={this.handleSizeChange}
					conference={this.state.conference}
				/>
			

            </React.Fragment>
        );
    }
}

export default Conferences;