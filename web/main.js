

//--------------------Variable----------------------

var numrow = 0;
var tbody = document.getElementById('table1');


//--------------------Variable----------------------
//--------------------connect firebase--------------


//--------------------connect firebase--------------
//--------------------Function----------------------

//----------------Show Table-----------


function AddItemToTable(cccd,name,Oxy,bodytemp,heartbeat){

    let trow = document.createElement("tr");
    let td1 = document.createElement('td');
    let td2 = document.createElement('td');
    let td3 = document.createElement('td');
    let td4 = document.createElement('td');
    let td5 = document.createElement('td');
    let td6 = document.createElement('td');

    td1.innerHTML = ++numrow;
    td2.innerHTML = cccd;
    td3.innerHTML = name;
    td4.innerHTML = Oxy;
    td5.innerHTML = bodytemp;
    td6.innerHTML = heartbeat;

    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    trow.appendChild(td5);
    trow.appendChild(td6);

    tbody.appendChild(trow);
}

function AddAllItemToTable(Custom){
    numrow=0;
    tbody.innerHTML = "";
    Custom.forEach(element =>{
      
        AddItemToTable(element.CCCD,element.Name,element.Oxy,element.TempBody,element.Heartbeat);
    });
    console.log(Custom);
    
}
//----------------Show Table-----------

//----------------Show Map-------------
function map(cus){
    
    var map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM()
          })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([105.84233, 20.96615]),
            zoom: 13
          }) 
      });
      var markers = new ol.layer.Vector({
        source: new ol.source.Vector(),
        style: new ol.style.Style({
          image: new ol.style.Icon({
            anchor: [0.5, 1],
            src: '1.png',
            scale: 0.05
          })    
        })
      });
      map.addLayer(markers);
      console.log(cus.length)
      
      //  console.log('2',JSON.parse(cus));
      var location = cus.map(x=>x.Location);
      console.log(location);
      var maker = [];
      
      for(var i=0;i< location.length;i++){
        maker[i] = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat(JSON.parse(location[i]))));
        markers.getSource().addFeature(maker[i]);
      }
      
    }
//----------------Show Map-------------
//----------------Show Table-----------

var num = 0;
var tbody2 = document.getElementById('table');
function AddCusToTable(CCCD,name,Status,SDT){

  let trow = document.createElement("tr");
  let td1 = document.createElement('td');
  let td2 = document.createElement('td');
  let td3 = document.createElement('td');
  let td4 = document.createElement('td');
  let td5 = document.createElement('td');

  td1.innerHTML = ++num;
  td2.innerHTML = CCCD;
  td3.innerHTML = name;
  td4.innerHTML = Status;
  td5.innerHTML = SDT;
  

  trow.appendChild(td1);
  trow.appendChild(td2);
  trow.appendChild(td3);
  trow.appendChild(td4);
  trow.appendChild(td5);
  

  tbody2.appendChild(trow);
}
function AddDataToTable(Custom){
  numrow=0;
  tbody2.innerHTML="";
  Custom.forEach(element =>{
    if(element.Oxy < 92 || element.TempBody > 38 || element.Heartbeat > 80){
      AddCusToTable(element.CCCD,element.Name,guess(element.Oxy,element.TempBody,element.Heartbeat),element.Phone);
    }
  });
  
}
function guess(oxy,temp,heart){
  var arr = new Array();
  if(oxy < 92){
    arr.push("Nồng độ Oxy < 92% ( "+ oxy +"% )");
  }
  if(heart > 80){
    arr.push("Nhịp tim cao ("+heart+")");
  }
  if(temp>38){
    arr.push("Sốt ("+temp+" °C)");
  }
  return arr;
  
}
//----------------Show Table-----------
//-----------------Send Mess-----------
function getAddress(){
  return document.getElementById('add').value;
}
function getMessage(){
  return document.getElementById('mes').value;
}

//-----------------Send Mess-----------

//--------------------Function----------------------