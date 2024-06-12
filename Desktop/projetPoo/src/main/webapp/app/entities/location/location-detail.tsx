import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">Location</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="datedebut">Datedebut</span>
          </dt>
          <dd>{locationEntity.datedebut ? <TextFormat value={locationEntity.datedebut} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="datefin">Datefin</span>
          </dt>
          <dd>{locationEntity.datefin ? <TextFormat value={locationEntity.datefin} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="prix">Prix</span>
          </dt>
          <dd>{locationEntity.prix}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
