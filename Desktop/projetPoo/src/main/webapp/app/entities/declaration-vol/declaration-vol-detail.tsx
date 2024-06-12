import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './declaration-vol.reducer';

export const DeclarationVolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const declarationVolEntity = useAppSelector(state => state.declarationVol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="declarationVolDetailsHeading">Declaration Vol</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{declarationVolEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{declarationVolEntity.type}</dd>
          <dt>
            <span id="marque">Marque</span>
          </dt>
          <dd>{declarationVolEntity.marque}</dd>
          <dt>
            <span id="modele">Modele</span>
          </dt>
          <dd>{declarationVolEntity.modele}</dd>
          <dt>
            <span id="numeroserie">Numeroserie</span>
          </dt>
          <dd>{declarationVolEntity.numeroserie}</dd>
          <dt>
            <span id="datevol">Datevol</span>
          </dt>
          <dd>
            {declarationVolEntity.datevol ? <TextFormat value={declarationVolEntity.datevol} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/declaration-vol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/declaration-vol/${declarationVolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeclarationVolDetail;
