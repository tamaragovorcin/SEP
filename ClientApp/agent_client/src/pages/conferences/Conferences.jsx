import React from "react";
import Header from "../../components/conferences/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL_AGENT } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";


class Conferences extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            conferences: [],
            key: 1 | props.activeKey,
            hiddenEditInfo: true,
            price: "",
            quantity: "",
            name: "",
            id: "",
            user: "",
            product: "",
            showOrderModal: false,
            pictures: [],
            openModal: false,
            help: [],
            fileUploadOngoing: false,
            albumId: "",


        }

        this.handleSelect = this.handleSelect.bind(this);
        this.onDrop = this.onDrop.bind(this);
    }


    hasRole = (reqRole) => {
		
		
		let roles =  ""
		if (localStorage === null) return false;

		roles = JSON.parse(localStorage.getItem("keyRole"));
		
		if (roles === null) return false;

		if (reqRole === "*") return true;

		
		if (roles.trim() === reqRole) 
		{
			
			return true;
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

    removeProduct = (idd) => {

        Axios.get(BASE_URL_AGENT + "/api/product/remove/" + idd,  {  headers: { Authorization: getAuthHeader() } })
            .then((res) => {

                console.log(res.data)
                alert("success")
                window.location.reload();

            })
            .catch((err) => {
                console.log(err);
            });

    }





    componentDidMount() {

    
    }

    render() {
        return (
            <React.Fragment>
                <TopBar />
                <Header />


                <div  id="hero" className="d-flex align-items-center" >
                    <div className="container-fluid">

                        <table className="table">
                            <tbody>
                               

                            </tbody>
                        </table>
                    </div>
                </div>

            </React.Fragment>
        );
    }
}

export default Conferences;