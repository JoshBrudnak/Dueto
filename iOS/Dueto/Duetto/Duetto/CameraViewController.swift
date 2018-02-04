//
//  CameraViewController.swift
//  Duetto
//
//  Created by Laith Rafidi on 2/4/18.
//  Copyright © 2018 Lily Suau. All rights reserved.
//

import UIKit

class CameraViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate{
    
    @IBOutlet var PhotoLibrary: UIButton!
    @IBOutlet var Camera: UIButton!
    @IBOutlet var ImageDisplay: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    @IBAction func PhotoLibraryAction(_ sender: UIButton) {
        
        let picker = UIImagePickerController()
        
        
        
        
    }
    @IBAction func CameraAction(_ sender: UIButton) {
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
