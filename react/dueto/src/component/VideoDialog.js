import React, { Component } from 'react'
import { Typography, Button } from 'material-ui'
import Dialog, {DialogContent} from 'material-ui/Dialog'
import {getVideoUrl} from "../utils/fetchData.js"

class VideoDialog extends Component {
  getVideoUrl() {
    let image = getVideoUrl(this.props.videoId)
    
    return image
  }

  render() {
    return (
        <Dialog open={this.props.open} onClose={this.props.close}>
          <DialogContent>
            <video controls style={{maxWidth: 500}}>
              <source src={this.getVideoUrl()} type="video/mp4"/>
            </video>
            <Typography variant="headline" component="h2">
              {this.props.name}
            </Typography>
            <Typography>
              {this.props.desc}
            </Typography>
            <Button size="small" color="primary" onClick={this.props.back}>Back</Button>
          </DialogContent> 
        </Dialog>
    )
  }
}

export default VideoDialog
