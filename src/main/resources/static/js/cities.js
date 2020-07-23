function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}


var citiesApi = Vue.resource('/cities{/id}');

Vue.component('city-form', {
    props: ['cities', 'cityAttr'],
    data: function() {
        return {
            id: '',
            cityName: '',
            cityDescription: ''
        }
    },
    watch: {
        cityAttr: function(newVal, oldVal) {
            this.id = newVal.id;
            this.cityName = newVal.cityName;
            this.cityDescription = newVal.cityDescription;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Название города" v-model="cityName" />' +
        '<input type="text" placeholder="Информация о городе" v-model="cityDescription" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var city = { cityName: this.cityName, cityDescription: this.cityDescription };

            if (this.id) {
                citiesApi.update({id: this.id}, city).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.cities, data.id);
                        this.cities.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )
            } else {
                citiesApi.save({}, city).then(result =>
                    result.json().then(data => {
                        this.cities.push(data);
                        this.text = ''
                    })
                )
            }
        }
    }
});

Vue.component('city-row', {
    props: ['city', 'editMethod', 'cities'],
    template: '<div>' +
        '<i>({{ city.id }})</i> {{ city.cityName }} {{ city.cityDescription}}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.city);
        },
        del: function() {
            citiesApi.remove({id: this.city.id}).then(result => {
                if (result.ok) {
                    this.cities.splice(this.cities.indexOf(this.city), 1)
                }
            })
        }
    }
});

Vue.component('cities-list', {
    props: ['cities'],
    data: function() {
        return {
            city: null
        }
    },
    template:
        '<div style="position: relative; width: 700px;">' +

        '<city-form :cities="cities" :cityAttr="city" />' +

        '<city-row v-for="city in cities" :key="city.id" :city="city" ' +
        ':editMethod="editMethod" :cities="cities" />' +
        '</div>',
    created: function() {
        citiesApi.get().then(result =>
            result.json().then(data =>
                data.forEach(city => this.cities.push(city))
            )
        )
    },
    methods: {
        editMethod: function(city) {
            this.city = city;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<cities-list :cities="cities" />',
    data: {
        cities: []
    }
});