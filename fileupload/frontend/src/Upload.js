
import React from 'react';
import {Button} from 'reactstrap';
import {withRouter} from 'react-router-dom';

class Upload extends React.Component {
    render() {
        return (
            <div>
                <form method="POST" enctype="multipart/form-data" action="/api/file_upload">
                    <input type="file" name="my_file" id="my_file" />
                    <Button type="submit" onClick={()=> {}}>Upload</Button>
                </form>
            </div>
        )
    }

}
export default withRouter(Upload);