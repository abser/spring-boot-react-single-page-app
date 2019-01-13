
import React from 'react';
import {Button} from 'reactstrap';
import {withRouter} from 'react-router-dom';

class Upload extends React.Component {
    render() {
        return (
            <div>
                <input type="file" name="my_file" id="my_file" />
                <Button onClick={()=> {}}>Upload</Button>
            </div>
        )
    }

}
export default withRouter(Upload);