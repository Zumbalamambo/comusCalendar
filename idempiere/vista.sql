-- View: comus_citasandroid

-- DROP VIEW comus_citasandroid;

CREATE OR REPLACE VIEW comus_citasandroid AS 
 SELECT r.ad_client_id,
    r.ad_org_id,
    r.isactive,
    r.created,
    r.createdby,
    r.updated,
    r.updatedby,
    r.starttime,
        CASE
            WHEN r.endtime IS NULL THEN r.starttime + '01:00:00'::interval
            ELSE r.endtime
        END AS endtime,
    u2.email,
    u2.password,
        CASE
            WHEN u.name IS NULL THEN ra.name::text
            ELSE (((COALESCE(u.name::text, ''::text) || '-'::text) || COALESCE(u.description::text, ''::text)) || '-'::text) || COALESCE(u.phone2::text, ''::text)
        END AS description
   FROM r_request r
     JOIN r_requesttype rt ON r.r_requesttype_id = rt.r_requesttype_id
     JOIN s_resourceassignment ra ON ra.s_resourceassignment_id = r.s_resourceassignment_id
     JOIN s_resource rs ON rs.s_resource_id = ra.s_resource_id
     LEFT JOIN ad_user u ON u.ad_user_id = r.ad_user_id
     JOIN ad_user u2 ON rs.ad_user_id = u2.ad_user_id
     JOIN s_resourcetype rst ON rst.s_resourcetype_id = rs.s_resourcetype_id
  WHERE r.isactive = 'Y'::bpchar AND r.r_requesttype_id = 1000000::numeric
  ORDER BY r.starttime DESC;

ALTER TABLE comus_citasandroid
  OWNER TO adempiere;

