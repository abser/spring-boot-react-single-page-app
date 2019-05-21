
import React from 'react';
import {Button} from 'reactstrap';
import {withRouter} from 'react-router-dom';
import axios from 'axios'

class Upload extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inProgress: false,
            uploadedNRIC: null
        }
    }

    handleFileUpload(e) {
        e.preventDefault();
        const formData = new FormData();
        let file = document.querySelector('#my_file');
        formData.append("my_file", file.files[0]);
        axios.post('api/file_upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(res => {
            console.log(res);
            this.setState({
                uploadedNRIC: res.data
            })
        })
    }

    showUploadedNRIC(nrics) {
        if(nrics.length > 0 ) {
            const row = [];
            nrics.forEach(nric => {
                row.push(<tr>
                    <td>{nric}</td>
                    <td>...</td>
                </tr>)
            });
            return(
                <table>
                    <tr>
                        <th>Uploaded Data</th>
                    </tr>
                    {row}
                </table>
            )
        }
    }
    
    render() {
        return (
            <div>
                <form method="POST" encType="multipart/form-data">
                    <input type="file" name="my_file" id="my_file" />
                    <Button type="submit" onClick={this.handleFileUpload.bind(this)}>Upload</Button>
                </form>
                <p>{this.state.uploadedNRIC ? this.showUploadedNRIC(this.state.uploadedNRIC) : null}</p>
                <div>
                    
                </div>
            </div>
        )
    }

}
export default withRouter(Upload);