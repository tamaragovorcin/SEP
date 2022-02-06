import React, { useEffect,useState } from "react";
import Axios from "axios";
import QRCode from 'qrcode.react';
import '../App.css';
import { Button } from "react-bootstrap";

const QRCodePayment = () => {   
    const [qrCodeText, setQRCodeText] = useState('');
    const [pan, setPan] = useState('');


    useEffect(() => {

        let webShopId = getCookie("webShopId");
        let totalPrice = getCookie("totalPrice");


        const dto = {
            amount: totalPrice,
            webshopId: webShopId
        }
        Axios.post("http://localhost:9090/bank-card-service/api/bankcard/create", dto)
            .then((res) => {
                console.log(res.data)
                document.cookie = "paymentId=" + res.data.paymentId + ";path=/";
            }
                )
                .catch(err => console.log(err));

        Axios.get("http://localhost:9090/qr-code-service/api/qr/merchantPAN/"+webShopId, dto)
        .then((res) => {
            const merchantPAN = res.data;
            let paymentId = getCookie("paymentId");

            const text = '{"paymentId":"'+paymentId+'","acquirerAccountNumber":"'+merchantPAN+'","AcquirerName":"Merchant name","Amount":'+totalPrice+',"Currency":"EUR"}';
            setQRCodeText(text)
            setPan(merchantPAN)

            
        }
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

    function handleSameBank() {
        if(pan.toString().startsWith("111")) {
            let dto = userFromAdiko();
            makePayment(dto)
        }
        else {
            let dto = userFromErste();
            makePayment(dto)
        }
    }

    function handleDifferentBank() {
        if(pan.toString().startsWith("111")) {
            let dto = userFromErste();
            makePayment(dto)
        }
        else {
            let dto = userFromAdiko();
            makePayment(dto)
        }

    }
    function userFromAdiko() {
        return {
            cardHolderName: "QR Test3",
            cardSecurityCode: "047",
            pan: "1112912400",
            expirationDate: "2026-02",
            webshopId: getCookie("webShopId")
        };
    }

    function userFromErste() {
        return {
            cardHolderName: "QR Test2",
            cardSecurityCode: "207",
            pan:"2223317440",
            expirationDate: "2026-02",
            webshopId: getCookie("webShopId")
        };
    }
    function makePayment(dto) {
      const paymentId = getCookie("paymentId")
      
        Axios.post("http://localhost:8088/payment/confirm/" + paymentId, dto, { validateStatus: () => true })
            .then((res) => {
                console.log(res.data)
            })
            .catch((err) => {
                console.log(err);
            });         
                
    }
	return (
        <React.Fragment>
             <div>
                <div class = "div-class-1">
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <h1>
                                        QR Code
                                    </h1>
                                </td>
                            </tr>
                            <tr></tr>
                            <tr>
                                <td>
                                    <QRCode
                                        id="qrCodeEl"
                                        size={900}
                                        value={qrCodeText}
                                    />
                                </td>
                            </tr>
                        

                        </tbody>
                    </table>
                </div>
                <div >
                    <table>
                        <tbody>
                             <tr> 
                            <td>
                            <div className="form-group">
								<Button
									style={{ background: "#1977cc", marginLeft: "40%", width: "20%" }}
									onClick={handleSameBank}
									className="btn btn-primary btn-xl"
									id="sendMessageButton"
									type="button"
								>
									Same bank
									</Button>
							</div>
                            </td>
                            <td>
                            <div className="form-group">
								<Button
									style={{ background: "#1977cc", marginLeft: "40%", width: "20%" }}
									onClick={handleDifferentBank}
									className="btn btn-primary btn-xl"
									id="sendMessageButton"
									type="button"
								>
									Different bank
									</Button>
							</div>
                            </td>
                        </tr>
                
                        </tbody>
                </table>
            </div>
            </div>
        </React.Fragment>
	);
}

export default QRCodePayment;


