
/*
 This is where you will be getting your data from a different source.
 */

import UIKit

class Data {
    
    static func getData(completion: @escaping ([Model]) -> ()) {
        DispatchQueue.global(qos: .userInteractive).async {
            var data = [Model]()
            data.append(Model(title: "Title", subTitle: "Subtitle", image: nil))
            
            DispatchQueue.main.async {
                completion(data)
            }
        }
    }
}
