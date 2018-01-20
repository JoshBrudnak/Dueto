//
//  buttonController.swift
//  Dueto
//
//  Created by Lily Suau on 1/15/18.
//  Copyright © 2018 Dueto. All rights reserved.
//

import UIKit
import Floaty

class buttonController: UIViewController {

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
                let floaty = Floaty()
                floaty.addItem("Home", icon:UIImage(named: "button3")!)
                self.view.addSubview(floaty)
        
     
    }

    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

}
