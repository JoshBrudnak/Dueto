//
//  ViewController.swift
//  duetoTestz
//
//  Created by Lily Suau on 1/12/18.
//  Copyright © 2018 Lily Suau. All rights reserved.
//

import UIKit
struct cellData {
    let cell : Int!
    let text : String!
    let image : UIImage!
    
}

class TableViewController: UITableViewController {
    
    var arrayOfCellData = [cellData]()
    
    override func viewDidLoad() {
        arrayOfCellData = [cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "Octocat")),
                           cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "android studio")), cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "Octocat"))]
        
        
        
        
    }
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayOfCellData.count
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = Bundle.main.loadNibNamed("TableViewCell1", owner: self, options: nil)?.first as! TableViewCell1
        cell.mainImageView.image = arrayOfCellData[indexPath.row].image
        cell.mainLabel.text = arrayOfCellData[indexPath.row].text
        
        return cell
        
        
    }
    
    
}
