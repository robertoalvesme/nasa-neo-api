package br.com.rhfactor.nasaneoapi;

import br.com.rhfactor.nasaneoapi.dtos.CloseApproachData;
import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import br.com.rhfactor.nasaneoapi.dtos.NearObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestingObjectConvertion {

    @Test
    void testNearObjectConversion() throws JsonProcessingException {

        String stringJson = "{\"neo_reference_id\":\"2518735\",\"name\":\"518735 (2009 JL1)\",\"is_potentially_hazardous_asteroid\":false,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"relative_velocity\":{\"kilometers_per_second\":\"8.8216987559\",\"kilometers_per_hour\":\"31758.1155210996\",\"miles_per_hour\":\"19733.2579469855\"},\"orbiting_body\":\"Earth\"}]}";
        NearObject response = new ObjectMapper().readValue(stringJson, NearObject.class);

        LocalDate expectedDate = LocalDate.of(1986, 07, 03);

        assertThat(response, allOf(
                hasProperty("neoReferenceId", equalTo(2518735L))
                , hasProperty("name", equalTo("518735 (2009 JL1)"))
                , hasProperty("approachList", allOf(
                        hasSize(1)
                ))
        ));

        CloseApproachData approach = response.getApproachList().get(0);
        assertThat(approach, allOf(
                hasProperty("cloaseApproachDate", is(expectedDate))
                , hasProperty("orbitingBody", equalTo("Earth"))
                , hasProperty("relativeVelocity")
        ));

        Map<String, Double> map = response.getApproachList().get(0).getRelativeVelocity();
        assertThat(map.size(), equalTo(3));

        // Verificar se o parametro necessário existe
        assertThat(map, IsMapContaining.hasKey("kilometers_per_hour"));

    }

    @Test
    void testNasaResponseConversion() throws JsonProcessingException {

        String stringJson = "{\"links\":{\"next\":\"http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-04&end_date=1986-07-04&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\",\"prev\":\"http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-02&end_date=1986-07-02&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\",\"self\":\"http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-03&end_date=1986-07-03&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"element_count\":6,\"near_earth_objects\":{\"1986-07-03\":[{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/2518735?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"2518735\",\"neo_reference_id\":\"2518735\",\"name\":\"518735 (2009 JL1)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2518735\",\"absolute_magnitude_h\":21.1,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.160160338,\"estimated_diameter_max\":0.358129403},\"meters\":{\"estimated_diameter_min\":160.1603379786,\"estimated_diameter_max\":358.1294030194},\"miles\":{\"estimated_diameter_min\":0.0995189894,\"estimated_diameter_max\":0.2225312253},\"feet\":{\"estimated_diameter_min\":525.4604432536,\"estimated_diameter_max\":1174.9652706022}},\"is_potentially_hazardous_asteroid\":false,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 13:23\",\"epoch_date_close_approach\":520780980000,\"relative_velocity\":{\"kilometers_per_second\":\"8.8216987559\",\"kilometers_per_hour\":\"31758.1155210996\",\"miles_per_hour\":\"19733.2579469855\"},\"miss_distance\":{\"astronomical\":\"0.1764344629\",\"lunar\":\"68.6330060681\",\"kilometers\":\"26394219.844434023\",\"miles\":\"16400607.7050465974\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false},{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/3157022?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"3157022\",\"neo_reference_id\":\"3157022\",\"name\":\"(2003 MS2)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3157022\",\"absolute_magnitude_h\":21.29,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.1467421834,\"estimated_diameter_max\":0.3281254972},\"meters\":{\"estimated_diameter_min\":146.7421833608,\"estimated_diameter_max\":328.1254971615},\"miles\":{\"estimated_diameter_min\":0.0911813372,\"estimated_diameter_max\":0.2038876683},\"feet\":{\"estimated_diameter_min\":481.4376248575,\"estimated_diameter_max\":1076.5272561075}},\"is_potentially_hazardous_asteroid\":true,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 01:42\",\"epoch_date_close_approach\":520738920000,\"relative_velocity\":{\"kilometers_per_second\":\"10.8404784529\",\"kilometers_per_hour\":\"39025.7224303522\",\"miles_per_hour\":\"24249.0662512374\"},\"miss_distance\":{\"astronomical\":\"0.033446438\",\"lunar\":\"13.010664382\",\"kilometers\":\"5003515.88388706\",\"miles\":\"3109040.602119028\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false},{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/3414251?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"3414251\",\"neo_reference_id\":\"3414251\",\"name\":\"(2008 LW16)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3414251\",\"absolute_magnitude_h\":20,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.2658,\"estimated_diameter_max\":0.5943468684},\"meters\":{\"estimated_diameter_min\":265.8,\"estimated_diameter_max\":594.3468684194},\"miles\":{\"estimated_diameter_min\":0.1651604118,\"estimated_diameter_max\":0.369309908},\"feet\":{\"estimated_diameter_min\":872.047272,\"estimated_diameter_max\":1949.9569797852}},\"is_potentially_hazardous_asteroid\":true,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 05:11\",\"epoch_date_close_approach\":520751460000,\"relative_velocity\":{\"kilometers_per_second\":\"14.8433999511\",\"kilometers_per_hour\":\"53436.2398240018\",\"miles_per_hour\":\"33203.2013506415\"},\"miss_distance\":{\"astronomical\":\"0.1836497247\",\"lunar\":\"71.4397429083\",\"kilometers\":\"27473607.641206389\",\"miles\":\"17071308.1811666082\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false},{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/3825562?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"3825562\",\"neo_reference_id\":\"3825562\",\"name\":\"(2018 NV)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3825562\",\"absolute_magnitude_h\":22.8,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.0732073989,\"estimated_diameter_max\":0.1636967205},\"meters\":{\"estimated_diameter_min\":73.2073989347,\"estimated_diameter_max\":163.696720474},\"miles\":{\"estimated_diameter_min\":0.0454889547,\"estimated_diameter_max\":0.1017163949},\"feet\":{\"estimated_diameter_min\":240.181762721,\"estimated_diameter_max\":537.0627483999}},\"is_potentially_hazardous_asteroid\":false,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 22:31\",\"epoch_date_close_approach\":520813860000,\"relative_velocity\":{\"kilometers_per_second\":\"16.856481743\",\"kilometers_per_hour\":\"60683.3342749112\",\"miles_per_hour\":\"37706.2640109858\"},\"miss_distance\":{\"astronomical\":\"0.4090026486\",\"lunar\":\"159.1020303054\",\"kilometers\":\"61185925.054918482\",\"miles\":\"38019170.8567477716\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false},{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/3989095?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"3989095\",\"neo_reference_id\":\"3989095\",\"name\":\"(2020 BN5)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3989095\",\"absolute_magnitude_h\":20.9,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.1756123185,\"estimated_diameter_max\":0.3926810818},\"meters\":{\"estimated_diameter_min\":175.6123184804,\"estimated_diameter_max\":392.6810818086},\"miles\":{\"estimated_diameter_min\":0.1091204019,\"estimated_diameter_max\":0.2440006365},\"feet\":{\"estimated_diameter_min\":576.1559189633,\"estimated_diameter_max\":1288.3238004408}},\"is_potentially_hazardous_asteroid\":false,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 04:42\",\"epoch_date_close_approach\":520749720000,\"relative_velocity\":{\"kilometers_per_second\":\"23.9240786311\",\"kilometers_per_hour\":\"86126.6830720709\",\"miles_per_hour\":\"53515.7714899765\"},\"miss_distance\":{\"astronomical\":\"0.3187860023\",\"lunar\":\"124.0077548947\",\"kilometers\":\"47689706.929895101\",\"miles\":\"29633009.7853143538\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false},{\"links\":{\"self\":\"http://www.neowsapp.com/rest/v1/neo/54054504?api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63\"},\"id\":\"54054504\",\"neo_reference_id\":\"54054504\",\"name\":\"(2020 SO1)\",\"nasa_jpl_url\":\"http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=54054504\",\"absolute_magnitude_h\":27.1,\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":0.0101054342,\"estimated_diameter_max\":0.0225964377},\"meters\":{\"estimated_diameter_min\":10.1054341542,\"estimated_diameter_max\":22.5964377109},\"miles\":{\"estimated_diameter_min\":0.0062792237,\"estimated_diameter_max\":0.0140407711},\"feet\":{\"estimated_diameter_min\":33.1543125905,\"estimated_diameter_max\":74.1352966996}},\"is_potentially_hazardous_asteroid\":false,\"close_approach_data\":[{\"close_approach_date\":\"1986-07-03\",\"close_approach_date_full\":\"1986-Jul-03 20:24\",\"epoch_date_close_approach\":520806240000,\"relative_velocity\":{\"kilometers_per_second\":\"18.590774468\",\"kilometers_per_hour\":\"66926.7880847449\",\"miles_per_hour\":\"41585.703407435\"},\"miss_distance\":{\"astronomical\":\"0.4696112841\",\"lunar\":\"182.6787895149\",\"kilometers\":\"70252847.829324867\",\"miles\":\"43653095.4202104846\"},\"orbiting_body\":\"Earth\"}],\"is_sentry_object\":false}]}}";
        NasaResponse response = new ObjectMapper().readValue(stringJson, NasaResponse.class);

        assertThat(response, allOf(
                hasProperty("elements", equalTo(6)),
                hasProperty("nearObjects")
        ));


        assertThat(response.getNearObjects().size(), equalTo(1));
        assertThat(response.getNearObjects(), IsMapContaining.hasKey("1986-07-03"));

        List<NearObject> nearObjects = response.getNearObjects().get("1986-07-03");
        assertThat(nearObjects, allOf(
                hasSize(6)
                , everyItem(
                    hasProperty("approachList", allOf(
                            hasSize(greaterThanOrEqualTo(1))
                            , everyItem(
                                    hasProperty("relativeVelocity", allOf(
                                            IsMapContaining.hasKey("kilometers_per_hour")
                                    ))
                            )
                    ))
                )
        ));

    }

}
