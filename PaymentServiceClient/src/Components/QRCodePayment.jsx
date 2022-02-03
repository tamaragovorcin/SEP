import React, { useEffect,useState } from "react";
import Axios from "axios";
import QRCode from 'qrcode.react';
import '../App.css';

const QRCodePayment = () => {   
    const [qrCodeText, setQRCodeText] = useState('');


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
	return (
        <React.Fragment>

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
        </React.Fragment>
	);
}

export default QRCodePayment;