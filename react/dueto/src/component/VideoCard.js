import React, { Component } from "react"
import { Card, Button, Typography } from "material-ui"
import { CardMedia, CardContent, CardActions } from "material-ui/Card"
import Dialog, {DialogContent} from "material-ui/Dialog"
import VideoDialog from "./VideoDialog.js"
import { Link } from "react-router-dom"
import { getThumbnailUrl, shareVideo } from "../utils/fetchData.js"

class VideoCard extends Component {
  constructor() {
    super()
  
    this.state = {
      open: false,
      openD: false
    }
  }

  getVideoImage() {
    let image = getThumbnailUrl(this.props.id)
    
    return image
  }

  viewVideo = () => {
    this.setState({open: true})
  }

  closeDialog = () => {
    this.setState({open: false})
  }

  shareVid = () => {
    shareVideo(this.props.id)
      .then(data => {
      })
      .catch(error => {
        console.error(error)
      })
    this.setState({openD: false})
  }

  share = () => {
    this.setState({openD: true})
  }

  closeDialog = () => {
    this.setState({openD: false})
  }

  profile = () => {
    document.getElementById("art").click()
  }

  render() {
    const profileUrl = "/artist/" + this.props.artist   

    return (
      <Card style={{width: 400, maxHeight: 350, margin: 40}}>
        <CardMedia>
          <img alt={this.props.name} src={this.getVideoImage()} style={{width: 400}}/>
        </CardMedia>
        <CardContent>
          <Typography variant="headline" component="h2">
            {this.props.name}
          </Typography>
          <Typography>
            {this.props.desc}
          </Typography>
        </CardContent>
        <CardActions>
          <Button onClick={this.viewVideo} size="small" color="primary">
            View  
          </Button>
          <Button onClick={this.profile} size="small" color="primary">
            Artist 
          </Button>
		  <Button onClick={this.share} size="small" color="primary">
			Share 
		  </Button>
        </CardActions> 
        <VideoDialog 
          close={this.closeDialog}
          open={this.state.open}
          videoId={this.props.id}
          artist={this.props.artist}
          desc={this.props.desc}
          name={this.props.name}
        />
        <Link id="art" to={profileUrl} style={{visibility: "collapsed"}}/>
        <Dialog open={this.state.openD} onClose={this.closeDialog}>
          <DialogContent>
            <Typography variant="headline" component="h2">
              Are you sure you want to share this video
            </Typography>
            <Button style={{marginTop: 10}} size="small" color="primary" onClick={this.shareVid}>Share</Button>
            <Button style={{marginTop: 10, marginLeft: 10}} size="small" color="primary" onClick={this.closeDialog}>Cancel</Button>
          </DialogContent>
        </Dialog>
      </Card>
    )
  }
}

export default VideoCard
