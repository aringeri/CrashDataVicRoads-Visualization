function updateTextInput(val) {
  document.getElementById('textInput').value=val;
}

function initMap() {
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 15,
    center: { lat: -37.98862, lng: 145.21805}
  });

  var selectionCircle;
  var infoWindow = new google.maps.InfoWindow();
  map.addListener('click', function(event) {
    if ($("#selection").is(':checked')) {
        var lat = event.latLng.lat();
        var lng = event.latLng.lng();
        var radiusM = Number($("#slider").val());
        var radiusKm = radiusM / 1000.0;
        console.log(radiusKm);

        infoWindow.close();
        clear(map.data);
        map.data.loadGeoJson("eventsWithin?lat=" + lat + "&lon=" + lng + "&radius=" + radiusKm);

        if (selectionCircle != null) {
          selectionCircle.setMap(null); //clear previous selection
        }
        selectionCircle = createCircle(
            map,
            new google.maps.LatLng(lat, lng),
            radiusM);
    }
  });

  // Set event listener for each feature.
  map.data.addListener('click', function(event) {
    infoWindow.setContent(event.feature.getProperty('name')+"<br>"+event.feature.getProperty('description'));
    infoWindow.setPosition(event.latLng);
    infoWindow.setOptions({pixelOffset: new google.maps.Size(0,-34)});
    infoWindow.open(map);
  });
}

function createCircle(map, latLng, radiusM) {
  return new google.maps.Circle({
      strokeColor: '#FF0000',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#FF0000',
      fillOpacity: 0.35,
      map: map,
      center: latLng,
      radius: radiusM
  });
}

function clear(data) {
  data.forEach(function(feature) {
    data.remove(feature);
  });
}