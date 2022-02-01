import React from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { Link } from "react-router-dom";

class HomePage extends React.Component {
	hasRole = (reqRole) => {
		
		
		let roles =  ""
		if (localStorage === null) return false;

		roles = localStorage.getItem("keyRole").substring(1, localStorage.getItem('keyRole').length-1)
		
		if (roles === null) return false;

		if (reqRole === "*") return true;

		
		if (roles.trim() === reqRole) 
		{
			
			return true;
		}
		return false;
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />

				<section id="hero"style={{marginTop:"10rem"}}>
					<div className="container" >
                    <section class="section-tours" id="section-tours">
                    <div class="u-center-text u-margin-bottom-big">
                        <h2 class="heading-secondary">
                             Choose Web Shop
                        </h2>
                    </div>

                    <div class="row">

                        
                        <div class="col-1-of-4">
                        <div class="card">
                            <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--1">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span">Equipment Web Shop</span>
                                    </h4>
                                    <div class="card__details">
                                     
                                    </div>
                                
                            </div>
                            <div class="card__side card__side--back">
                                    <div class="card__cta">
                                   
                                        <Link href="#popup" class="btn btn-primary" to="/all">Choose!</Link>
                                    </div>
                                </div>
                        </div>
                        </div>


                        <div class="col-1-of-4">
                            <div class="card">
                                <div class="card__side card__side--front">
                                    <div class="card__picture card__picture--2">
                                        &nbsp;
                                    </div>
                                    <h4 class="card__heading">
                                        <span class="card__heading-span">Conferences/courses Web Shop</span>
                                    </h4>
                                    <div class="card__details">
                                       
                                    </div>

                                </div>
                                <div class="card__side card__side--back ">
                                    <div class="card__cta">
                                
                                        <Link href="#popup" class="btn btn-primary" to="/conferences">Choose!</Link>
                                    </div>
                                </div>
                            </div>
                        </div>

                      
                    </div>
                </section>
					</div>
					
				</section>

				<a href="#" className="back-to-top">
					<i className="icofont-simple-up"></i>
				</a>
			</React.Fragment>
		);
	}
}

export default HomePage;
